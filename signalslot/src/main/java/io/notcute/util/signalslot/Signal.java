package io.notcute.util.signalslot;

import io.notcute.util.collections.ConcurrentHashSet;

import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static io.notcute.util.signalslot.Connection.Type.*;

/**
 * The base class of all signals. Note: Connecting and emitting slots
 * concurrently is thread-safe without blocking.
 */
public abstract class Signal<R> {

	/**
	 * Indicates whether a signal is enabled/disabled.
	 * @see #enable()
	 * @see #disable()
	 */
	private final AtomicBoolean enabled = new AtomicBoolean(true);

	/**
	 * The queue of dispatched connections.
	 * @see Dispatcher
	 */
	private final Queue<Connection> connections = new ConcurrentLinkedQueue<>();

	/**
	 * The set of unique slots.
	 * @see Connection.Type#UNIQUE
	 */
	private final Set<Slot<R>> uniques = new ConcurrentHashSet<>();

	/**
	 * Enables this signal.
	 * @see #disable()
	 */
	public void enable() {
		enabled.set(true);
	}

	/**
	 * Disables this signal. A disabled signal will not actuate its connected
	 * slots.
	 * @see #enable()
	 */
	public void disable() {
		enabled.set(false);
	}

	/**
	 * Removes all connected slots. Clearing a signal is not an atomic
	 * operation and may result in a non-empty slot queue if one of the
	 * 'connect' methods is used concurrently.
	 */
	public void clear() {
		connections.clear();
		uniques.clear();
	}

	/**
	 * Connects the given slot using {@link Connection.Type#AUTO}. This method is
	 * equivalent to {@code connect(slot, AUTO)}.
	 *
	 * @see #connect(Slot, int)
	 * @param slot The slot to connect.
	 * @throws NullPointerException If {@code slot} is {@code null}.
	 */
	public Connection connect(final Slot<R> slot) {
		return connect(slot, AUTO);
	}

	/**
	 * Connects the given slot according to {@link Connection.Type}. This method is
	 * equivalent to {@code connect(slot, type, null)}.
	 *
	 * @see #connect(Slot, Dispatcher, int)
	 * @param slot The slot to connect.
	 * @param type The connection type.
	 * @throws NullPointerException If {@code slot} is {@code null}.
	 */
	public Connection connect(final Slot<R> slot, final int type) {
		return connect(slot, null, type);
	}

	/**
	 * Connects the given slot according to {@link Connection.Type#AUTO}
	 * and actuates it within the thread context of the
	 * given {@link Dispatcher} if the signal is emitted.
	 * If {@link Dispatcher} is null, the default {@link Dispatcher} will be used.
	 * This method is equivalent to {@code connect(slot, dispatcher, AUTO)}.
	 *
	 * @see #connect(Slot, Dispatcher, int)
	 * @param slot The slot to connect.
	 * @param dispatcher The {@link Dispatcher} to use.
	 * @throws NullPointerException If {@code slot} is {@code null}.
	 */
	public Connection connect(final Slot<R> slot, final Dispatcher dispatcher) {
		return connect(slot, dispatcher, AUTO);
	}

	/**
	 * Connects the given slot according to {@link Connection.Type}
	 * and actuates it within the thread context of the
	 * given {@link Dispatcher} if the signal is emitted.
	 * If {@link Dispatcher} is null, the default {@link Dispatcher} will be used.
	 *
	 * @param slot The slot to connect.
	 * @param type The connection type.
	 * @param dispatcher The {@link Dispatcher} to use.
	 * @throws NullPointerException If {@code slot} is {@code null}.
	 */
	public Connection connect(final Slot<R> slot, Dispatcher dispatcher, int type) throws IllegalArgumentException {
		Objects.requireNonNull(slot);
		if (dispatcher == null) dispatcher = Dispatcher.getDefaultDispatcher();
		final boolean unique = (type & UNIQUE) == UNIQUE;
		final boolean singleShot = (type & SINGLE_SHOT) == SINGLE_SHOT;
		type = type << 29 >>> 29;
		boolean broken = false;
		if (uniques.contains(slot)) broken = true;
		else if (unique) uniques.add(slot);
		final Connection conn;
		switch (type) {
			case AUTO:
			case DIRECT:
			case QUEUED:
			case BLOCKING_QUEUED:
				connections.add((conn = new Connection(slot, type, dispatcher, singleShot, broken)));
				break;
			default:
				throw new IllegalArgumentException("Invalid connection type: " + type);
		}
		return conn;
	}

	public boolean disconnect(final Connection conn) {
		if (conn == null) return false;
		if (!connections.contains(conn)) return false;
		conn.setBroken(true);
		connections.remove(conn);
		return true;
	}

	public boolean disconnect(final Slot<R> slot) {
		if (slot == null) return disconnect();
		for (final Connection connection : connections) {
			if (connection.slot == slot) {
				connection.setBroken(true);
				connections.remove(connection);
				return true;
			}
		}
		return false;
	}

	public boolean disconnect(Dispatcher dispatcher) {
		if (dispatcher == null) dispatcher = Dispatcher.getDefaultDispatcher();
		for (final Connection connection : connections) {
			if (connection.dispatcher == dispatcher) {
				connection.setBroken(true);
				connections.remove(connection);
				return true;
			}
		}
		return false;
	}

	public boolean disconnect(final Slot<R> slot, final Dispatcher dispatcher) {
		if (slot == null) return disconnect(dispatcher);
		for (final Connection connection : connections) {
			if (connection.dispatcher == dispatcher && connection.slot == slot) {
				connection.setBroken(true);
				connections.remove(connection);
				return true;
			}
		}
		return false;
	}

	public boolean disconnect() {
		for (final Connection connection : connections) {
			connection.setBroken(true);
			connections.remove(connection);
			return true;
		}
		return false;
	}

	/**
	 * Emits this signal with the given arguments.
	 *
	 * @param args The arguments to use pass to the connected slots.
	 */
	protected R invoke(final Object... args) {
		if (enabled.get()) {
			R result = null;
			for (final Connection connection : connections) {
				if (!connection.isBroken()) result = connection.dispatcher.actuate(new SlotActuation(connection, args));
				else connections.remove(connection);
			}
			return result;
		}
		else return null;
	}

	/**
	 * A callback method used for slot actuation.
	 *
	 * The implementer of this method does not need to create any threads, but
	 * cast down the given slot and actuate it with the given arguments.
	 *
	 * This method should not have any side effects to this class.
	 *
	 * @param slot The slot to actuate.
	 * @param args The arguments of the actuated slot.
	 */
	protected abstract R actuate(final Slot<?> slot, final Object... args);

	/**
	 * Stores a connection and its arguments and allows to actuate this slot with
	 * {@link #actuate()}.
	 */
	class SlotActuation {

		/**
		 * The connection to actuate.
		 */
		private final Connection connection;

		/**
		 * The arguments to pass to {@link #connection}.
		 */
		private final Object[] arguments;

		/**
		 * The result.
		 */
		private final AtomicReference<R> result = new AtomicReference<>(null);

		/**
		 * Creates a new instance with given slot and arguments.
		 *
		 * @param conn The connection to store.
		 * @param args The arguments to store.
		 * @throws NullPointerException If {@code s} or {@code args} is
		 * {@code null} ({@code args} may contain {@code null} values though).
		 */
		private SlotActuation(final Connection conn, final Object... args) {
			connection = Objects.requireNonNull(conn);
			arguments = Objects.requireNonNull(args);
		}

		/**
		 * Gets the connection.
		 * @return the connection
		 */
		Connection getConnection() {
			return connection;
		}

		/**
		 * Actuates {@link #connection} with its arguments {@link #arguments}.
		 */
		void actuate() {
			result.set(Signal.this.actuate(connection.slot, arguments));
			if (connection.singleShot) {
				connection.setBroken(true);
				connections.remove(connection);
			}
		}

		/**
		 * Gets the result.
		 * @return the result
		 */
		public R result() {
			return result.get();
		}
		
	}

}

package io.notcute.util.signalslot;

import io.notcute.util.signalslot.Signal.SlotActuation;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import static io.notcute.util.signalslot.Connection.Type.*;

/**
 * This class allows to actuate dispatched slots.
 *
 * A dispatched slot is actuated in a separate 'dispatcher' thread, e.g., the
 * GUI thread, the database thread, and so on. One way of doing this is to
 * periodically call {@link #waitFor()} and {@link #dispatch()} in a thread of
 * your choice to handle the actuation of slots.
 *
 * You may also use {@link #run()} which calls {@link #waitFor()} and
 * {@link #switchContext()} in a loop until the current thread gets
 * interrupted. {@link #switchContext()}, in turn, may be subclassed to
 * delegate {@link #dispatch()} to another dispatcher thread, for example, the
 * JavaFX thread. By default, {@link #switchContext()} calls
 * {@link #dispatch()} in the same context as {@link #run()} which is a useful
 * default in most cases.
 */
public abstract class Dispatcher {

	public static Dispatcher getDefaultDispatcher() {
		return SimpleDispatcher.getDefaultDispatcher();
	}

	/**
	 * Is used to block the dispatcher thread until an associated signal has
	 * been emitted.
	 */
	private final Semaphore semaphore = new Semaphore(0);

	/**
	 * Is used to block the signalling thread
	 * if connection type is {@link Connection.Type#BLOCKING_QUEUED}
	 * until an associated signal has been emitted.
	 */
	private final Semaphore blocking = new Semaphore(0);

	/**
	 * Is used to prevent the deadlock of {@link #blocking}
	 */
	private volatile boolean blockingReleased;

	/**
	 * The queue of slots to actuate. Needs to be thread safe without locking.
	 */
	private final Queue<Signal<?>.SlotActuation> slots = new ConcurrentLinkedQueue<>();

	/**
	 * Is emitted by {@link #dispatch()} if a {@link RuntimeException} has been
	 * thrown by either {@link #beforeActuation()}, {@link #afterActuation()},
	 * or a slot actuation itself.
	 */
	private final VoidSignal1<RuntimeException> onError = new VoidSignal1<>();

	protected abstract boolean isDispatchThread();

	/**
	 * Adds the given {@link SlotActuation} to the event queue. The slot itself
	 * gets actuated by the next call of {@link #dispatch()}.
	 *
	 * @param slotActuation The {@link SlotActuation} to add.
	 * @throws NullPointerException If {@code slotActuation} is {@code null}.
	 */
	final<R> R actuate(final Signal<R>.SlotActuation slotActuation) {
		Objects.requireNonNull(slotActuation);
		final Connection conn = slotActuation.getConnection();
		int connectionType = conn.type;
		if (connectionType == AUTO) {
			if (isDispatchThread()) connectionType = DIRECT;
			else connectionType = QUEUED;
		}
		if (connectionType == QUEUED && !(conn.slot instanceof VoidSlot)) connectionType = BLOCKING_QUEUED;
		if (connectionType == DIRECT) {
			slotActuation.actuate();
		}
		else {
			slots.add(slotActuation);
			if (connectionType == BLOCKING_QUEUED) blockingReleased = false;
			semaphore.release();
			if (connectionType == BLOCKING_QUEUED) {
				if (!blockingReleased) {
					try {
						blocking.acquire();
					} catch (final InterruptedException e) {
						onError.emit(new RuntimeException(e));
					}
				}
			}
		}
		return slotActuation.result();
	}

	/**
	 * Blocks the current thread until a slot needs to be actuated. Throws an
	 * {@link InterruptedException} if the threads gets interrupted while
	 * waiting.
	 *
	 * @throws InterruptedException If the the current thread was interrupted.
	 */
	@SuppressWarnings("WeakerAccess")
	protected final void waitFor() throws InterruptedException {
		semaphore.acquire();
	}

	/**
	 * Polls the next {@link SlotActuation} from the event queue and actuates
	 * it. Does nothing if the event queue is empty. This function will never
	 * throw a {@link RuntimeException}, but emit {@link #onError()}.
	 */
	protected final void dispatch() {
		try {
			final Signal<?>.SlotActuation sa = slots.poll();
			if (sa != null) {
				final Connection conn = sa.getConnection();
				beforeActuation();
				sa.actuate();
				afterActuation();
				int connectionType = conn.type;
				if (connectionType == AUTO) connectionType = QUEUED;
				if (connectionType == QUEUED && !(conn.slot instanceof VoidSlot)) connectionType = BLOCKING_QUEUED;
				if (connectionType == BLOCKING_QUEUED) {
					blocking.release();
					blockingReleased = true;
				}
			}
		} catch (final RuntimeException e) {
			onError.emit(e);
		}
	}

	/**
	 * Allows subclasses to switch the thread context before actuating a slot
	 * by calling {@link #dispatch()} within the desired context. The default
	 * implementation calls {@link #dispatch()} within the caller context.
	 */
	protected abstract void switchContext();

	/**
	 * This is a callback which gets executed by {@link #dispatch()} right
	 * before a slot is actuated. Override it to add some custom code. If a
	 * {@link RuntimeException} is thrown by this callback, {@link #dispatch()}
	 * will catch it and emit the signal returned by {@link #onError()}. If
	 * there is no slot to actuate {@link #dispatch()} omits this callback.
	 */
	protected void beforeActuation() {}

	/**
	 * This is a callback which gets executed by {@link #dispatch()} right
	 * after a slot has been actuated. Override it to add some custom code. If
	 * a {@link RuntimeException} is thrown by this callback,
	 * {@link #dispatch()} will catch it and emit the signal returned by
	 * {@link #onError()}. If there is no slot to actuate {@link #dispatch()}
	 * omits this callback.
	 */
	protected void afterActuation() {}

	/**
	 * Returns the signal which gets emitted if actuating a slot failed.
	 *
	 * @return The signal which gets emitted if actuating a slot failed.
	 */
	public final VoidSignal1<RuntimeException> onError() {
		return onError;
	}

	protected void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				waitFor();
				switchContext();
			}
		} catch (final InterruptedException e) { /**/ }
	}

}

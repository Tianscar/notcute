package io.notcute.util.signalslot;

import java.util.Objects;

public class Connection {

    /**
     * The constants of supported connection types.
     * <p>
     * To actuate a slot by a {@link Dispatcher} use
     * {@link Signal#connect(Slot, Dispatcher)}.
     *
     * @see Dispatcher
     */
    public static final class Type {

        private Type() {
            throw new UnsupportedOperationException();
        }

        /**
         * (Default) If the receiver lives in the thread that emits the signal,
         * {@link Type#DIRECT} is used.
         * Otherwise, {@link Type#QUEUED} is used.
         * The connection type is determined when the signal is emitted.
         */
        public static final int AUTO = 0;

        /**
         * The slot is invoked immediately when the signal is emitted. The slot is executed in the signalling thread.
         */
        public static final int DIRECT = 1;

        /**
         * The slot is invoked when control returns to the event loop of the receiver's thread. The slot is executed in the receiver's thread.
         * This connection will be treated as {@link Type#BLOCKING_QUEUED} for {@link Slot} which are not {@link VoidSlot}.
         */
        public static final int QUEUED = 2;

        /**
         * Same as {@link Type#QUEUED}, except that the signalling thread blocks until the slot returns.
         * This connection must not be used if the receiver lives in the signalling thread, or else the application will deadlock.
         */
        public static final int BLOCKING_QUEUED = 3;

        /**
         * This is a flag that can be combined with any one of the above connection types, using a bitwise OR.
         * When {@link Type#UNIQUE} is set, {@link Signal#connect(Slot, int)} will fail if the connection already exists
         * (i.e. if the same signal is already connected to the same slot for the same pair of objects).
         * <p></p>
         * <p><b>
         * Note: {@link Type#UNIQUE} do not work for lambdas and functors;
         * they only apply to connecting to {@link DynamicVoidSlot}.
         * </b></p>
         */
        public static final int UNIQUE = 0x80;

        /**
         * This is a flag that can be combined with any one of the above connection types, using a bitwise OR.
         * When {@link Type#SINGLE_SHOT} is set, the slot is going to be called only once;
         * the connection will be automatically broken when the signal is emitted.
         */
        public static final int SINGLE_SHOT = 0x100;

    }

    /**
     * The slot to actuate.
     */
    final Slot<?> slot;

    /**
     * The connection type.
     */
    final int type;

    /**
     * Whether the connection is {@link Type#SINGLE_SHOT}.
     */
    final boolean singleShot;

    /**
     * The dispatcher to dispatch the connection.
     */
    final Dispatcher dispatcher;

    /**
     * Whether the connection is broken.
     *
     * @see Type#SINGLE_SHOT
     */
    private volatile boolean broken;

    public Connection(final Connection conn) {
        this(Objects.requireNonNull(conn).slot, conn.type, conn.dispatcher, conn.singleShot, conn.broken);
    }

    Connection(final Slot<?> s, final int type, final Dispatcher dispatcher, final boolean singleShot) {
        this(s, type, dispatcher, singleShot, false);
    }

    Connection(final Slot<?> s, final int type, final Dispatcher dispatcher, final boolean singleShot, final boolean broken) {
        this.slot = Objects.requireNonNull(s);
        this.dispatcher = Objects.requireNonNull(dispatcher);
        this.type = type;
        this.singleShot = singleShot;
        this.broken = broken;
    }

    void setBroken(final boolean broken) {
        this.broken = broken;
    }

    public boolean isBroken() {
        return broken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        if (type != that.type) return false;
        if (singleShot != that.singleShot) return false;
        if (isBroken() != that.isBroken()) return false;
        if (!slot.equals(that.slot)) return false;
        return dispatcher.equals(that.dispatcher);
    }

    @Override
    public int hashCode() {
        int result = slot.hashCode();
        result = 31 * result + type;
        result = 31 * result + (singleShot ? 1 : 0);
        result = 31 * result + dispatcher.hashCode();
        result = 31 * result + (isBroken() ? 1 : 0);
        return result;
    }

}

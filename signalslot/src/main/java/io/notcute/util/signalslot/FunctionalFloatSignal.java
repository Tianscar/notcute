package io.notcute.util.signalslot;

/**
 * The base class of all functional signals. Note: Connecting and emitting slots
 * concurrently is thread-safe without blocking.
 */
public abstract class FunctionalFloatSignal<T extends FunctionalFloatSlot> extends FloatSignal {

    protected float emit(Object... args) {
        return invoke(args);
    }

    /**
     * @see Signal#connect(Slot)
     */
    public Connection connect(T slot) {
        return super.connect(slot);
    }

    /**
     * @see Signal#connect(Slot, int)
     */
    public Connection connect(T slot, int type) {
        return super.connect(slot, type);
    }

    /**
     * @see Signal#connect(Slot, Dispatcher)
     */
    public Connection connect(T slot, Dispatcher dispatcher) {
        return super.connect(slot, dispatcher);
    }

    /**
     * @see Signal#connect(Slot, Dispatcher, int)
     */
    public Connection connect(T slot, Dispatcher dispatcher, int type) {
        return super.connect(slot, dispatcher, type);
    }

}

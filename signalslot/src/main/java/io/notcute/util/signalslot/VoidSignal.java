package io.notcute.util.signalslot;

/**
 * The base class of all void signals.
 */
public abstract class VoidSignal extends Signal<Void> {

    protected abstract void actuateVoid(final Slot<?> slot, final Object... args);

    @Override
    protected Void actuate(final Slot<?> slot, final Object... args) {
        actuateVoid(slot, args);
        return null;
    }

}

package io.notcute.util.signalslot;

/**
 * The base class of all float signals.
 */
public abstract class FloatSignal extends Signal<Float> {

    protected abstract float actuateFloat(final Slot<?> slot, final Object... args);

    @Override
    protected Float actuate(final Slot<?> slot, final Object... args) {
        return actuateFloat(slot, args);
    }

}

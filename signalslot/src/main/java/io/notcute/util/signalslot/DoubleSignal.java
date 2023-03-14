package io.notcute.util.signalslot;

/**
 * The base class of all double signals.
 */
public abstract class DoubleSignal extends Signal<Double> {

    protected abstract double actuateDouble(final Slot<?> slot, final Object... args);

    @Override
    protected Double actuate(final Slot<?> slot, final Object... args) {
        return actuateDouble(slot, args);
    }

}

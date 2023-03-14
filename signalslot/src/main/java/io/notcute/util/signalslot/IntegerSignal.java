package io.notcute.util.signalslot;

/**
 * The base class of all int signals.
 */
public abstract class IntegerSignal extends Signal<Integer> {

    protected abstract int actuateInteger(final Slot<?> slot, final Object... args);

    @Override
    protected Integer actuate(final Slot<?> slot, final Object... args) {
        return actuateInteger(slot, args);
    }

}

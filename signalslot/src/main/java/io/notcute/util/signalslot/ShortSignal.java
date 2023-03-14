package io.notcute.util.signalslot;

/**
 * The base class of all short signals.
 */
public abstract class ShortSignal extends Signal<Short> {

    protected abstract short actuateShort(final Slot<?> slot, final Object... args);

    @Override
    protected Short actuate(final Slot<?> slot, final Object... args) {
        return actuateShort(slot, args);
    }

}

package io.notcute.util.signalslot;

/**
 * The base class of all long signals.
 */
public abstract class LongSignal extends Signal<Long> {

    protected abstract long actuateLong(final Slot<?> slot, final Object... args);

    @Override
    protected Long actuate(final Slot<?> slot, final Object... args) {
        return actuateLong(slot, args);
    }

}

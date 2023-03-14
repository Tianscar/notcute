package io.notcute.util.signalslot;

/**
 * The base class of all boolean signals.
 */
public abstract class BooleanSignal extends Signal<Boolean> {

    protected abstract boolean actuateBoolean(final Slot<?> slot, final Object... args);

    @Override
    protected Boolean actuate(final Slot<?> slot, final Object... args) {
        return actuateBoolean(slot, args);
    }

}

package io.notcute.util.signalslot;

/**
 * The base class of all byte signals.
 */
public abstract class ByteSignal extends Signal<Byte> {

    protected abstract byte actuateByte(final Slot<?> slot, final Object... args);

    @Override
    protected Byte actuate(final Slot<?> slot, final Object... args) {
        return actuateByte(slot, args);
    }

}

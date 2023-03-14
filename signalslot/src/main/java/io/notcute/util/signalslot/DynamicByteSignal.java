package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicByteSignal extends ByteSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public byte emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected byte actuateByte(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicByteSlot) return ((DynamicByteSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

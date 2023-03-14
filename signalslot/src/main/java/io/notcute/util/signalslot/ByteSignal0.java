package io.notcute.util.signalslot;

/**
 * A byte signal with no argument.
 */
public class ByteSignal0 extends FunctionalByteSignal<ByteSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public byte emit() {
		return super.emit();
	}

	@Override
	protected byte actuateByte(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicByteSlot) return ((DynamicByteSlot) slot).accept(args);
		else if (slot instanceof ByteSlot0)  return  ((ByteSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

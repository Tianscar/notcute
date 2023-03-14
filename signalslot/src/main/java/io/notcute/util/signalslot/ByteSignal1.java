package io.notcute.util.signalslot;

/**
 * A byte signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class ByteSignal1<A> extends FunctionalByteSignal<ByteSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public byte emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected byte actuateByte(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicByteSlot) return ((DynamicByteSlot) slot).accept(args);
		else if (slot instanceof ByteSlot1)  return  ((ByteSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

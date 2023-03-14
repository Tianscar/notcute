package io.notcute.util.signalslot;

/**
 * A byte signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class ByteSignal3<A, B, C> extends FunctionalByteSignal<ByteSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public byte emit(A a, B b, C c) {
		return super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected byte actuateByte(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicByteSlot) return ((DynamicByteSlot) slot).accept(args);
		else if (slot instanceof ByteSlot3)  return  ((ByteSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

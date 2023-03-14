package io.notcute.util.signalslot;

/**
 * A float signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class FloatSignal1<A> extends FunctionalFloatSignal<FloatSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected float actuateFloat(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else if (slot instanceof FloatSlot1)  return  ((FloatSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

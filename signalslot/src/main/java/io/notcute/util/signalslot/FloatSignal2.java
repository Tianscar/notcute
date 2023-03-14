package io.notcute.util.signalslot;

/**
 * A float signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
public class FloatSignal2<A, B> extends FunctionalFloatSignal<FloatSlot2<A, B>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit(A a, B b) {
		return super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected float actuateFloat(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else if (slot instanceof FloatSlot2)  return  ((FloatSlot2<A, B>) slot).accept((A)args[0], (B)args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

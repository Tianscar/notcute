package io.notcute.util.signalslot;

/**
 * A float signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class FloatSignal3<A, B, C> extends FunctionalFloatSignal<FloatSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit(A a, B b, C c) {
		return super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected float actuateFloat(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else if (slot instanceof FloatSlot3)  return  ((FloatSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

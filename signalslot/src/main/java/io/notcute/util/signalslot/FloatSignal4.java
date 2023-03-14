package io.notcute.util.signalslot;

/**
 * A float signal with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 */
public class FloatSignal4<A, B, C, D> extends FunctionalFloatSignal<FloatSlot4<A, B, C, D>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit(A a, B b, C c, D d) {
		return super.emit(a, b, c, d);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected float actuateFloat(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else if (slot instanceof FloatSlot4)  return  ((FloatSlot4<A, B, C, D>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

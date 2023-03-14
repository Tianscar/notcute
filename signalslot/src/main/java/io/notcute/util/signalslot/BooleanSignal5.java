package io.notcute.util.signalslot;

/**
 * A boolean signal with 5 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 */
public class BooleanSignal5<A, B, C, D, E> extends FunctionalBooleanSignal<BooleanSlot5<A, B, C, D, E>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public boolean emit(A a, B b, C c, D d, E e) {
		return super.emit(a, b, c, d, e);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean actuateBoolean(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicBooleanSlot) return ((DynamicBooleanSlot) slot).accept(args);
		else if (slot instanceof BooleanSlot5)  return  ((BooleanSlot5<A, B, C, D, E>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3], (E)args[4]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

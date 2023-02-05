package io.notcute.util.signalslot;

/**
 * A void signal with 5 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 */
public class VoidSignal5<A, B, C, D, E> extends FunctionalVoidSignal<VoidSlot5<A, B, C, D, E>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit(A a, B b, C c, D d, E e) {
		super.emit(a, b, c, d, e);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void actuateVoid(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else if (slot instanceof VoidSlot5) ((VoidSlot5<A, B, C, D, E>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3], (E)args[4]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

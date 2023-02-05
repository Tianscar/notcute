package io.notcute.util.signalslot;

/**
 * A void signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
public class VoidSignal2<A, B> extends FunctionalVoidSignal<VoidSlot2<A, B>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit(A a, B b) {
		super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void actuateVoid(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else if (slot instanceof VoidSlot2) ((VoidSlot2<A, B>) slot).accept((A)args[0], (B)args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

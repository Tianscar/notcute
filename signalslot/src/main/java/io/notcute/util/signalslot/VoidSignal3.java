package io.notcute.util.signalslot;

/**
 * A void signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class VoidSignal3<A, B, C> extends FunctionalVoidSignal<VoidSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit(A a, B b, C c) {
		super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void actuateVoid(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else if (slot instanceof VoidSlot3) ((VoidSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

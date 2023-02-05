package io.notcute.util.signalslot;

/**
 * A void signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class VoidSignal1<A> extends FunctionalVoidSignal<VoidSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit(A a) {
		super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void actuateVoid(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else if (slot instanceof VoidSlot1) ((VoidSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

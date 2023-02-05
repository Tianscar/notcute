package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicVoidSignal extends VoidSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit(final Object... args) {
		super.invoke(args);
	}

	@Override
	protected void actuateVoid(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

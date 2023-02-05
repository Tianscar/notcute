package io.notcute.util.signalslot;

/**
 * A void signal with no argument.
 */
public class VoidSignal0 extends FunctionalVoidSignal<VoidSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public void emit() {
		super.emit();
	}

	@Override
	protected void actuateVoid(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicVoidSlot) ((DynamicVoidSlot) slot).accept(args);
		else if (slot instanceof VoidSlot0) ((VoidSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

package io.notcute.util.signalslot;

/**
 * A boolean signal with no argument.
 */
public class BooleanSignal0 extends FunctionalBooleanSignal<BooleanSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public boolean emit() {
		return super.emit();
	}

	@Override
	protected boolean actuateBoolean(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicBooleanSlot) return ((DynamicBooleanSlot) slot).accept(args);
		else if (slot instanceof BooleanSlot0)  return  ((BooleanSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

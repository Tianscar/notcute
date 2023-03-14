package io.notcute.util.signalslot;

/**
 * A int signal with no argument.
 */
public class IntegerSignal0 extends FunctionalIntegerSignal<IntegerSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public int emit() {
		return super.emit();
	}

	@Override
	protected int actuateInteger(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicIntegerSlot) return ((DynamicIntegerSlot) slot).accept(args);
		else if (slot instanceof IntegerSlot0)  return  ((IntegerSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

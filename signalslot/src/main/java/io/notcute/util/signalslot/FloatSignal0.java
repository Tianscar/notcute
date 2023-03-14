package io.notcute.util.signalslot;

/**
 * A float signal with no argument.
 */
public class FloatSignal0 extends FunctionalFloatSignal<FloatSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit() {
		return super.emit();
	}

	@Override
	protected float actuateFloat(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else if (slot instanceof FloatSlot0)  return  ((FloatSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

package io.notcute.util.signalslot;

/**
 * A double signal with no argument.
 */
public class DoubleSignal0 extends FunctionalDoubleSignal<DoubleSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public double emit() {
		return super.emit();
	}

	@Override
	protected double actuateDouble(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicDoubleSlot) return ((DynamicDoubleSlot) slot).accept(args);
		else if (slot instanceof DoubleSlot0)  return  ((DoubleSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

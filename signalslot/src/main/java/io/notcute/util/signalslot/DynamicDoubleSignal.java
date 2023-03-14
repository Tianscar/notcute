package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicDoubleSignal extends DoubleSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public double emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected double actuateDouble(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicDoubleSlot) return ((DynamicDoubleSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

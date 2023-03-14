package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicFloatSignal extends FloatSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public float emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected float actuateFloat(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicFloatSlot) return ((DynamicFloatSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

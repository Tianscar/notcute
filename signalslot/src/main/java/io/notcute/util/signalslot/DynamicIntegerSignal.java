package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicIntegerSignal extends IntegerSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public int emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected int actuateInteger(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicIntegerSlot) return ((DynamicIntegerSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

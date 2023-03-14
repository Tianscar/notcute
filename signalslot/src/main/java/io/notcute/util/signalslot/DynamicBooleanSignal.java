package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicBooleanSignal extends BooleanSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public boolean emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected boolean actuateBoolean(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicBooleanSlot) return ((DynamicBooleanSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

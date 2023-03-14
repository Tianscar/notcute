package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicLongSignal extends LongSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public long emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected long actuateLong(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicLongSlot) return ((DynamicLongSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

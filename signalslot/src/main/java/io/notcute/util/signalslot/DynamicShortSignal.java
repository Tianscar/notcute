package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicShortSignal extends ShortSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public short emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected short actuateShort(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicShortSlot) return ((DynamicShortSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

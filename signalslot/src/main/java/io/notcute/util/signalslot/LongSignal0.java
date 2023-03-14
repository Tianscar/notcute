package io.notcute.util.signalslot;

/**
 * A long signal with no argument.
 */
public class LongSignal0 extends FunctionalLongSignal<LongSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public long emit() {
		return super.emit();
	}

	@Override
	protected long actuateLong(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicLongSlot) return ((DynamicLongSlot) slot).accept(args);
		else if (slot instanceof LongSlot0)  return  ((LongSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

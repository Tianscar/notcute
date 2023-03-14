package io.notcute.util.signalslot;

/**
 * A short signal with no argument.
 */
public class ShortSignal0 extends FunctionalShortSignal<ShortSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public short emit() {
		return super.emit();
	}

	@Override
	protected short actuateShort(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicShortSlot) return ((DynamicShortSlot) slot).accept(args);
		else if (slot instanceof ShortSlot0)  return  ((ShortSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

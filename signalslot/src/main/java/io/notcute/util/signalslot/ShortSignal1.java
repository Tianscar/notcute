package io.notcute.util.signalslot;

/**
 * A short signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class ShortSignal1<A> extends FunctionalShortSignal<ShortSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public short emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected short actuateShort(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicShortSlot) return ((DynamicShortSlot) slot).accept(args);
		else if (slot instanceof ShortSlot1)  return  ((ShortSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

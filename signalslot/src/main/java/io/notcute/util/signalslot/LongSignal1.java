package io.notcute.util.signalslot;

/**
 * A long signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class LongSignal1<A> extends FunctionalLongSignal<LongSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public long emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected long actuateLong(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicLongSlot) return ((DynamicLongSlot) slot).accept(args);
		else if (slot instanceof LongSlot1)  return  ((LongSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

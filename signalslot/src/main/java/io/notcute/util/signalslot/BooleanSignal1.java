package io.notcute.util.signalslot;

/**
 * A boolean signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class BooleanSignal1<A> extends FunctionalBooleanSignal<BooleanSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public boolean emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean actuateBoolean(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicBooleanSlot) return ((DynamicBooleanSlot) slot).accept(args);
		else if (slot instanceof BooleanSlot1)  return  ((BooleanSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

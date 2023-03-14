package io.notcute.util.signalslot;

/**
 * A int signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class IntegerSignal1<A> extends FunctionalIntegerSignal<IntegerSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public int emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected int actuateInteger(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicIntegerSlot) return ((DynamicIntegerSlot) slot).accept(args);
		else if (slot instanceof IntegerSlot1)  return  ((IntegerSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

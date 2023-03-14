package io.notcute.util.signalslot;

/**
 * A double signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class DoubleSignal1<A> extends FunctionalDoubleSignal<DoubleSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public double emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected double actuateDouble(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicDoubleSlot) return ((DynamicDoubleSlot) slot).accept(args);
		else if (slot instanceof DoubleSlot1)  return  ((DoubleSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

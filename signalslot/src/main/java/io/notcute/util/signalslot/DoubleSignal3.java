package io.notcute.util.signalslot;

/**
 * A double signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class DoubleSignal3<A, B, C> extends FunctionalDoubleSignal<DoubleSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public double emit(A a, B b, C c) {
		return super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected double actuateDouble(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicDoubleSlot) return ((DynamicDoubleSlot) slot).accept(args);
		else if (slot instanceof DoubleSlot3)  return  ((DoubleSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

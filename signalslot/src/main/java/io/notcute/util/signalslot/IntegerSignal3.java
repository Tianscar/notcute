package io.notcute.util.signalslot;

/**
 * A int signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class IntegerSignal3<A, B, C> extends FunctionalIntegerSignal<IntegerSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public int emit(A a, B b, C c) {
		return super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected int actuateInteger(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicIntegerSlot) return ((DynamicIntegerSlot) slot).accept(args);
		else if (slot instanceof IntegerSlot3)  return  ((IntegerSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

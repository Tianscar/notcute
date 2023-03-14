package io.notcute.util.signalslot;

/**
 * A boolean signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
public class BooleanSignal2<A, B> extends FunctionalBooleanSignal<BooleanSlot2<A, B>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public boolean emit(A a, B b) {
		return super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean actuateBoolean(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicBooleanSlot) return ((DynamicBooleanSlot) slot).accept(args);
		else if (slot instanceof BooleanSlot2)  return  ((BooleanSlot2<A, B>) slot).accept((A)args[0], (B)args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

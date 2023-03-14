package io.notcute.util.signalslot;

/**
 * A long signal with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 */
public class LongSignal4<A, B, C, D> extends FunctionalLongSignal<LongSlot4<A, B, C, D>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public long emit(A a, B b, C c, D d) {
		return super.emit(a, b, c, d);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected long actuateLong(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicLongSlot) return ((DynamicLongSlot) slot).accept(args);
		else if (slot instanceof LongSlot4)  return  ((LongSlot4<A, B, C, D>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

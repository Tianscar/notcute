package io.notcute.util.signalslot;

/**
 * A long signal with 10 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 * @param <G> The type of the seventh argument.
 * @param <H> The type of the eighth argument.
 * @param <I> The type of the ninth argument.
 * @param <J> The type of the tenth argument.
 */
public class LongSignal10<A, B, C, D, E, F, G, H, I, J> extends FunctionalLongSignal<LongSlot10<A, B, C, D, E, F, G, H, I, J>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public long emit(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
		return super.emit(a, b, c, d, e, f, g, h, i, j);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected long actuateLong(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicLongSlot) return ((DynamicLongSlot) slot).accept(args);
		else if (slot instanceof LongSlot10)
 return 			((LongSlot10<A, B, C, D, E, F, G, H, I, J>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3], (E)args[4], (F)args[5], (G)args[6], (H)args[7], (I)args[8], (J)args[9]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

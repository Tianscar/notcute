package io.notcute.util.signalslot;

/**
 * A signal with 8 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 * @param <G> The type of the seventh argument.
 * @param <H> The type of the eighth argument.
 * @param <R> The type of the returned value.
 */
public class Signal8<A, B, C, D, E, F, G, H, R> extends FunctionalSignal<Slot8<A, B, C, D, E, F, G, H, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a, B b, C c, D d, E e, F f, G g, H h) {
		return super.emit(a, b, c, d, e, f, g, h);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot8) return ((Slot8<A, B, C, D, E, F, G, H, R>) slot)
				.accept((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5], (G) args[6], (H) args[7]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

package io.notcute.util.signalslot;

/**
 * A signal with 6 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 * @param <R> The type of the returned value.
 */
public class Signal6<A, B, C, D, E, F, R> extends FunctionalSignal<Slot6<A, B, C, D, E, F, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a, B b, C c, D d, E e, F f) {
		return super.emit(a, b, c, d, e, f);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot6) return ((Slot6<A, B, C, D, E, F, R>) slot).accept((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

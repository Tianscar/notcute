package io.notcute.util.signalslot;

/**
 * A signal with 5 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <R> The type of the returned value.
 */
public class Signal5<A, B, C, D, E, R> extends FunctionalSignal<Slot5<A, B, C, D, E, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a, B b, C c, D d, E e) {
		return super.emit(a, b, c, d, e);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot5) return ((Slot5<A, B, C, D, E, R>) slot).accept((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

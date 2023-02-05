package io.notcute.util.signalslot;

/**
 * A signal with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <R> The type of the returned value.
 */
public class Signal4<A, B, C, D, R> extends FunctionalSignal<Slot4<A, B, C, D, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a, B b, C c, D d) {
		return super.emit(a, b, c, d);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot4) return ((Slot4<A, B, C, D, R>) slot).accept((A) args[0], (B) args[1], (C) args[2], (D) args[3]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

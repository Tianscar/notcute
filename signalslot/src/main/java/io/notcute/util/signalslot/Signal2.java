package io.notcute.util.signalslot;

/**
 * A signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <R> The type of the returned value.
 */
public class Signal2<A, B, R> extends FunctionalSignal<Slot2<A, B, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a, B b) {
		return super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot2) return ((Slot2<A, B, R>) slot).accept((A) args[0], (B) args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

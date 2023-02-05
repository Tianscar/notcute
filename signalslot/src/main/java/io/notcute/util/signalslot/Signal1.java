package io.notcute.util.signalslot;

/**
 * A signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 * @param <R> The type of the returned value.
 */
public class Signal1<A, R> extends FunctionalSignal<Slot1<A, R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot1) return ((Slot1<A, R>) slot).accept((A) args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

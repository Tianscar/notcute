package io.notcute.util.signalslot;

/**
 * A signal with no argument.
 *
 * @param <R> The type of the returned value.
 */
public class Signal0<R> extends FunctionalSignal<Slot0<R>, R> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public R emit() {
		return super.emit();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected R actuate(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
		else if (slot instanceof Slot0) return ((Slot0<R>) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

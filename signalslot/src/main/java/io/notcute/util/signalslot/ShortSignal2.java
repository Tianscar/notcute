package io.notcute.util.signalslot;

/**
 * A short signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
public class ShortSignal2<A, B> extends FunctionalShortSignal<ShortSlot2<A, B>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public short emit(A a, B b) {
		return super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected short actuateShort(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicShortSlot) return ((DynamicShortSlot) slot).accept(args);
		else if (slot instanceof ShortSlot2)  return  ((ShortSlot2<A, B>) slot).accept((A)args[0], (B)args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

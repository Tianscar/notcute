package io.notcute.util.signalslot;

/**
 * A char signal with 6 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 */
public class CharacterSignal6<A, B, C, D, E, F> extends FunctionalCharacterSignal<CharacterSlot6<A, B, C, D, E, F>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit(A a, B b, C c, D d, E e, F f) {
		return super.emit(a, b, c, d, e, f);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected char actuateCharacter(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else if (slot instanceof CharacterSlot6)  return  ((CharacterSlot6<A, B, C, D, E, F>) slot).accept((A)args[0], (B)args[1], (C)args[2], (D)args[3], (E)args[4], (F)args[5]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

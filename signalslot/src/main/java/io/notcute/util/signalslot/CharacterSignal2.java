package io.notcute.util.signalslot;

/**
 * A char signal with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
public class CharacterSignal2<A, B> extends FunctionalCharacterSignal<CharacterSlot2<A, B>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit(A a, B b) {
		return super.emit(a, b);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected char actuateCharacter(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else if (slot instanceof CharacterSlot2)  return  ((CharacterSlot2<A, B>) slot).accept((A)args[0], (B)args[1]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

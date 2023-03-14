package io.notcute.util.signalslot;

/**
 * A char signal with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
public class CharacterSignal3<A, B, C> extends FunctionalCharacterSignal<CharacterSlot3<A, B, C>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit(A a, B b, C c) {
		return super.emit(a, b, c);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected char actuateCharacter(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else if (slot instanceof CharacterSlot3)  return  ((CharacterSlot3<A, B, C>) slot).accept((A)args[0], (B)args[1], (C)args[2]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

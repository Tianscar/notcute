package io.notcute.util.signalslot;

/**
 * A char signal with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
public class CharacterSignal1<A> extends FunctionalCharacterSignal<CharacterSlot1<A>> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit(A a) {
		return super.emit(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected char actuateCharacter(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else if (slot instanceof CharacterSlot1)  return  ((CharacterSlot1<A>) slot).accept((A)args[0]);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

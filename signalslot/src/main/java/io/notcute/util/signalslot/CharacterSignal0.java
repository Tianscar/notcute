package io.notcute.util.signalslot;

/**
 * A char signal with no argument.
 */
public class CharacterSignal0 extends FunctionalCharacterSignal<CharacterSlot0> {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit() {
		return super.emit();
	}

	@Override
	protected char actuateCharacter(Slot<?> slot, Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else if (slot instanceof CharacterSlot0)  return  ((CharacterSlot0) slot).accept();
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}

}

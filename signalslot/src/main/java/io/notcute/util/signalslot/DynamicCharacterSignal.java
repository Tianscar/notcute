package io.notcute.util.signalslot;

/**
 * A signal with specific object and unlimited arguments.
 */
public class DynamicCharacterSignal extends CharacterSignal {

	/**
	 * @see Signal#invoke(Object...)
	 */
	public char emit(final Object... args) {
		return super.invoke(args);
	}

	@Override
	protected char actuateCharacter(final Slot<?> slot, final Object... args) {
		if (slot instanceof DynamicCharacterSlot) return ((DynamicCharacterSlot) slot).accept(args);
		else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
	}
	
}

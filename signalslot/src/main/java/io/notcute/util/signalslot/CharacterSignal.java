package io.notcute.util.signalslot;

/**
 * The base class of all char signals.
 */
public abstract class CharacterSignal extends Signal<Character> {

    protected abstract char actuateCharacter(final Slot<?> slot, final Object... args);

    @Override
    protected Character actuate(final Slot<?> slot, final Object... args) {
        return actuateCharacter(slot, args);
    }

}

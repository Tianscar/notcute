package io.notcute.util.signalslot;

/**
 * A char slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface CharacterSlot1<A> extends FunctionalCharacterSlot {

	char accept(A a);

}

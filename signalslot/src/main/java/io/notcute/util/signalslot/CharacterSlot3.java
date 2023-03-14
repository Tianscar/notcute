package io.notcute.util.signalslot;

/**
 * A char slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
@FunctionalInterface
public interface CharacterSlot3<A, B, C> extends FunctionalCharacterSlot {

	char accept(A a, B b, C c);

}

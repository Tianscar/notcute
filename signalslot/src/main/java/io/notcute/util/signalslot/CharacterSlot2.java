package io.notcute.util.signalslot;

/**
 * A char slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface CharacterSlot2<A, B> extends FunctionalCharacterSlot {

	char accept(A a, B b);

}

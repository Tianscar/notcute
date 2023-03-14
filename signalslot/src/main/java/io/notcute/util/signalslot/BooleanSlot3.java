package io.notcute.util.signalslot;

/**
 * A boolean slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
@FunctionalInterface
public interface BooleanSlot3<A, B, C> extends FunctionalBooleanSlot {

	boolean accept(A a, B b, C c);

}

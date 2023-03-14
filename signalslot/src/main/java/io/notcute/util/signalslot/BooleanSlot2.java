package io.notcute.util.signalslot;

/**
 * A boolean slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface BooleanSlot2<A, B> extends FunctionalBooleanSlot {

	boolean accept(A a, B b);

}

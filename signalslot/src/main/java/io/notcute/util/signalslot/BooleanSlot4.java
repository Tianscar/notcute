package io.notcute.util.signalslot;

/**
 * A boolean slot with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 */
@FunctionalInterface
public interface BooleanSlot4<A, B, C, D> extends FunctionalBooleanSlot {

	boolean accept(A a, B b, C c, D d);

}

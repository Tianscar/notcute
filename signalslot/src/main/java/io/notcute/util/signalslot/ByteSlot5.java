package io.notcute.util.signalslot;

/**
 * A byte slot with 5 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 */
@FunctionalInterface
public interface ByteSlot5<A, B, C, D, E> extends FunctionalByteSlot {

	byte accept(A a, B b, C c, D d, E e);

}

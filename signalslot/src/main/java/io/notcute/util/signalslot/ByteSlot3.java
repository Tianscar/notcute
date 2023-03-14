package io.notcute.util.signalslot;

/**
 * A byte slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
@FunctionalInterface
public interface ByteSlot3<A, B, C> extends FunctionalByteSlot {

	byte accept(A a, B b, C c);

}

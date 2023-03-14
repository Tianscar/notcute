package io.notcute.util.signalslot;

/**
 * A byte slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface ByteSlot2<A, B> extends FunctionalByteSlot {

	byte accept(A a, B b);

}

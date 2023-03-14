package io.notcute.util.signalslot;

/**
 * A byte slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface ByteSlot1<A> extends FunctionalByteSlot {

	byte accept(A a);

}

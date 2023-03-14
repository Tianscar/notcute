package io.notcute.util.signalslot;

/**
 * A boolean slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface BooleanSlot1<A> extends FunctionalBooleanSlot {

	boolean accept(A a);

}

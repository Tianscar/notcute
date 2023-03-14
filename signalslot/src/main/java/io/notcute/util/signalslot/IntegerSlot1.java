package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A int slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface IntegerSlot1<A> extends FunctionalIntegerSlot {

	int accept(A a);

}

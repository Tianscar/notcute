package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A double slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface DoubleSlot1<A> extends FunctionalDoubleSlot {

	double accept(A a);

}

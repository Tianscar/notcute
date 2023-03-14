package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A long slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface LongSlot1<A> extends FunctionalLongSlot {

	long accept(A a);

}

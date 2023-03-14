package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A long slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface LongSlot2<A, B> extends FunctionalLongSlot {

	long accept(A a, B b);

}

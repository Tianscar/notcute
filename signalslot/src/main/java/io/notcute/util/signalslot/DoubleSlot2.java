package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A double slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface DoubleSlot2<A, B> extends FunctionalDoubleSlot {

	double accept(A a, B b);

}

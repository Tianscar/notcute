package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A int slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface IntegerSlot2<A, B> extends FunctionalIntegerSlot {

	int accept(A a, B b);

}

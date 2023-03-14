package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A float slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface FloatSlot2<A, B> extends FunctionalFloatSlot {

	float accept(A a, B b);

}

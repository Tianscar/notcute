package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A float slot with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 */
@FunctionalInterface
public interface FloatSlot4<A, B, C, D> extends FunctionalFloatSlot {

	float accept(A a, B b, C c, D d);

}

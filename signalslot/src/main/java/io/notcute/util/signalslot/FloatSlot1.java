package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A float slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface FloatSlot1<A> extends FunctionalFloatSlot {

	float accept(A a);

}

package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A int slot with 8 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 * @param <G> The type of the seventh argument.
 * @param <H> The type of the eighth argument.
 */
@FunctionalInterface
public interface IntegerSlot8<A, B, C, D, E, F, G, H> extends FunctionalIntegerSlot {

	int accept(A a, B b, C c, D d, E e, F f, G g, H h);

}

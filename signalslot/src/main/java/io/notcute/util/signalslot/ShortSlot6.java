package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A short slot with 6 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 */
@FunctionalInterface
public interface ShortSlot6<A, B, C, D, E, F> extends FunctionalShortSlot {

	short accept(A a, B b, C c, D d, E e, F f);

}

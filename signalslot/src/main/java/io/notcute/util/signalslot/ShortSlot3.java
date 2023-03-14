package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A short slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
@FunctionalInterface
public interface ShortSlot3<A, B, C> extends FunctionalShortSlot {

	short accept(A a, B b, C c);

}

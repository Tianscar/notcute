package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A short slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface ShortSlot2<A, B> extends FunctionalShortSlot {

	short accept(A a, B b);

}

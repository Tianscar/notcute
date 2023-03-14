package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A short slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface ShortSlot1<A> extends FunctionalShortSlot {

	short accept(A a);

}

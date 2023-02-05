package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 */
@FunctionalInterface
public interface VoidSlot3<A, B, C> extends FunctionalVoidSlot {

	void accept(A a, B b, C c);

	default VoidSlot3<A, B, C> andThen(
			VoidSlot3<? super A, ? super B, ? super C> after) {
		Objects.requireNonNull(after);
		return (a, b, c) -> {
			accept(a, b, c);
			after.accept(a, b, c);
		};
	}

}

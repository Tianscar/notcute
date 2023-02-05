package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 */
@FunctionalInterface
public interface VoidSlot2<A, B> extends FunctionalVoidSlot {

	void accept(A a, B b);

	default VoidSlot2<A, B> andThen(VoidSlot2<? super A, ? super B> after) {
		Objects.requireNonNull(after);
		return (a, b) -> {
			accept(a, b);
			after.accept(a, b);
		};
	}

}

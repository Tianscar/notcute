package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 5 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 */
@FunctionalInterface
public interface VoidSlot5<A, B, C, D, E> extends FunctionalVoidSlot {

	void accept(A a, B b, C c, D d, E e);

	default VoidSlot5<A, B, C, D, E> andThen(
			VoidSlot5<? super A, ? super B, ? super C, ? super D, ? super E> after) {
		Objects.requireNonNull(after);
		return (a, b, c, d, e) -> {
			accept(a, b, c, d, e);
			after.accept(a, b, c, d, e);
		};
	}

}

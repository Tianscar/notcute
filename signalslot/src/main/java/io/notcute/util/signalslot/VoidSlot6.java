package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 6 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 */
@FunctionalInterface
public interface VoidSlot6<A, B, C, D, E, F> extends FunctionalVoidSlot {

	void accept(A a, B b, C c, D d, E e, F f);

	default VoidSlot6<A, B, C, D, E, F> andThen(
			VoidSlot6<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> after) {
		Objects.requireNonNull(after);
		return (a, b, c, d, e, f) -> {
			accept(a, b, c, d, e, f);
			after.accept(a, b, c, d, e, f);
		};
	}

}

package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 4 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 */
@FunctionalInterface
public interface VoidSlot4<A, B, C, D> extends FunctionalVoidSlot {

	void accept(A a, B b, C c, D d);

	default VoidSlot4<A, B, C, D> andThen(
			VoidSlot4<? super A, ? super B, ? super C, ? super D> after) {
		Objects.requireNonNull(after);
		return (a, b, c, d) -> {
			accept(a, b, c, d);
			after.accept(a, b, c, d);
		};
	}

}

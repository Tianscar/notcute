package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 9 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the forth argument.
 * @param <E> The type of the fifth argument.
 * @param <F> The type of the sixth argument.
 * @param <G> The type of the seventh argument.
 * @param <H> The type of the eighth argument.
 * @param <I> The type of the ninth argument.
 */
@FunctionalInterface
public interface VoidSlot9<A, B, C, D, E, F, G, H, I> extends FunctionalVoidSlot {

	void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i);

	default VoidSlot9<A, B, C, D, E, F, G, H, I> andThen(
			VoidSlot9<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> after) {
		Objects.requireNonNull(after);
		return (a, b, c, d, e, f, g, h, i) -> {
			accept(a, b, c, d, e, f, g, h, i);
			after.accept(a, b, c, d, e, f, g, h, i);
		};
	}

}

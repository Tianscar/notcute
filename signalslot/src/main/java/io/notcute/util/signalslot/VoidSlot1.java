package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 */
@FunctionalInterface
public interface VoidSlot1<A> extends FunctionalVoidSlot {

	void accept(A a);

	default VoidSlot1<A> andThen(VoidSlot1<? super A> after) {
		Objects.requireNonNull(after);
		return (a) -> {
			accept(a);
			after.accept(a);
		};
	}

}

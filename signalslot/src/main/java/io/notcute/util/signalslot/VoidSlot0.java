package io.notcute.util.signalslot;

import java.util.Objects;

/**
 * A void slot with no argument.
 */
@FunctionalInterface
public interface VoidSlot0 extends FunctionalVoidSlot {

	void accept();
	
	default VoidSlot0 andThen(final VoidSlot0 after) {
		Objects.requireNonNull(after);
		return () -> {
			accept();
			after.accept();
		};
	}

}

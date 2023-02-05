package io.notcute.util.signalslot;

/**
 * A slot with 1 generic argument.
 *
 * @param <A> The type of the argument.
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface Slot1<A, R> extends FunctionalSlot<R> {

    R accept(A a);

}

package io.notcute.util.signalslot;

/**
 * A slot with 2 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface Slot2<A, B, R> extends FunctionalSlot<R> {

    R accept(A a, B b);

}

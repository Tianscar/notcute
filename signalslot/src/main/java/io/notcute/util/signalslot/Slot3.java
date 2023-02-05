package io.notcute.util.signalslot;

/**
 * A slot with 3 generic arguments.
 *
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface Slot3<A, B, C, R> extends FunctionalSlot<R> {

    R accept(A a, B b, C c);

}

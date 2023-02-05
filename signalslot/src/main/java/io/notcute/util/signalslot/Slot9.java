package io.notcute.util.signalslot;

/**
 * A slot with 9 generic arguments.
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
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface Slot9<A, B, C, D, E, F, G, H, I, R> extends FunctionalSlot<R> {

    R accept(A a, B b, C c, D d, E e, F f, G g, H h, I i);

}

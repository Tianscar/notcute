package io.notcute.util.signalslot;

/**
 * A slot with no argument.
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface Slot0<R> extends FunctionalSlot<R> {

    R accept();

}

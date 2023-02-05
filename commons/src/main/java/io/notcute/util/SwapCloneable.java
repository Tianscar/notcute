package io.notcute.util;
public interface SwapCloneable extends Cloneable, Swapable {

    @Override
    default void to(Object dst) {
        ((Swapable)dst).from(this);
    }

    @Override
    default void from(Object src) {
        ((Swapable)src).to(this);
    }

    @Override
    default void swap(Object other) {
        Object tmp = ((Cloneable) other).clone();
        to(other);
        from(tmp);
    }

}

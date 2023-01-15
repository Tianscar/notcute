package a3wt.util;

public interface A3Copyable<T extends A3Copyable<T>> extends Cloneable {

    T copy();

}

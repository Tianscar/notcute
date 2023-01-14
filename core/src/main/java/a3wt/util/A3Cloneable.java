package a3wt.util;

public interface A3Cloneable<T extends A3Cloneable<T>> extends Cloneable {

    T clone();

}

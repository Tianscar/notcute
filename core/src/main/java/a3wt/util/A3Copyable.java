package a3wt.util;

public interface A3Copyable<T extends A3Copyable<T>> {

    T copy();

    void to(T dst);
    void from(T src);

}

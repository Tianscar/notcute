package a3wt.util;

public interface A3MutableCopyable<T extends A3MutableCopyable<T>> extends A3Copyable<T> {

    void to(T dst);
    void from(T src);

}

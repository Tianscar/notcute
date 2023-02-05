package io.notcute.util;

@FunctionalInterface
public interface QuietCallable<T> {

    T call();

}

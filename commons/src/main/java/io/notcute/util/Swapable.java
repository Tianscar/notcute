package io.notcute.util;

public interface Swapable {

    void to(Object dst);
    void from(Object src);
    void swap(Object other);

}

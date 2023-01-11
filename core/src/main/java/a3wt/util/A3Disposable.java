package a3wt.util;

public interface A3Disposable {

    boolean isDisposed();
    void dispose();
    default void checkDisposed(String errorMessage) {
        if (isDisposed()) {
            throw new IllegalStateException(errorMessage);
        }
    }

}

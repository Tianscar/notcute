package a3wt.util;

public interface A3Prepareable {

    boolean isPrepared();
    void prepare() throws Exception;
    default void checkPrepared(String errorMessage) {
        if (isPrepared()) {
            throw new IllegalStateException(errorMessage);
        }
    }

}

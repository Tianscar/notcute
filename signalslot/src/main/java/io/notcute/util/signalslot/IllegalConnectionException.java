package io.notcute.util.signalslot;

public class IllegalConnectionException extends RuntimeException {

    private static final long serialVersionUID = -3907585654573217560L;

    public IllegalConnectionException() {
        super();
    }

    public IllegalConnectionException(String message) {
        super(message);
    }

    public IllegalConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalConnectionException(Throwable cause) {
        super(cause);
    }

}

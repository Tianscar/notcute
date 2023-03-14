package io.notcute.util.signalslot;

/**
 * A long slot with specific object, method name and unlimited argument types.
 */
public class DynamicLongSlot extends AbstractDynamicSlot implements LongSlot {

    public DynamicLongSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    long accept(Object... args) throws IllegalConnectionException {
        return (long) invoke(args);
    }

}

package io.notcute.util.signalslot;

/**
 * A byte slot with specific object, method name and unlimited argument types.
 */
public class DynamicByteSlot extends AbstractDynamicSlot implements ByteSlot {

    public DynamicByteSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    byte accept(Object... args) throws IllegalConnectionException {
        return (byte) invoke(args);
    }

}

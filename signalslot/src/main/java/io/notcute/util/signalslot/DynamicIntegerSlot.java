package io.notcute.util.signalslot;

/**
 * A int slot with specific object, method name and unlimited argument types.
 */
public class DynamicIntegerSlot extends AbstractDynamicSlot implements IntegerSlot {

    public DynamicIntegerSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    int accept(Object... args) throws IllegalConnectionException {
        return (int) invoke(args);
    }

}

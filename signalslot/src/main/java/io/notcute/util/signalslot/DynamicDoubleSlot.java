package io.notcute.util.signalslot;

/**
 * A double slot with specific object, method name and unlimited argument types.
 */
public class DynamicDoubleSlot extends AbstractDynamicSlot implements DoubleSlot {

    public DynamicDoubleSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    double accept(Object... args) throws IllegalConnectionException {
        return (double) invoke(args);
    }

}

package io.notcute.util.signalslot;

/**
 * A boolean slot with specific object, method name and unlimited argument types.
 */
public class DynamicBooleanSlot extends AbstractDynamicSlot implements BooleanSlot {

    public DynamicBooleanSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    boolean accept(Object... args) throws IllegalConnectionException {
        return (boolean) invoke(args);
    }

}

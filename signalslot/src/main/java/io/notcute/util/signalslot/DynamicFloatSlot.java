package io.notcute.util.signalslot;

/**
 * A float slot with specific object, method name and unlimited argument types.
 */
public class DynamicFloatSlot extends AbstractDynamicSlot implements FloatSlot {

    public DynamicFloatSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    float accept(Object... args) throws IllegalConnectionException {
        return (float) invoke(args);
    }

}

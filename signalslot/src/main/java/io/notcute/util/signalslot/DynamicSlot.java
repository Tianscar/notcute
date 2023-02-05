package io.notcute.util.signalslot;

public class DynamicSlot<R> extends AbstractDynamicSlot implements Slot<R> {

    public DynamicSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    @SuppressWarnings("unchecked")
    R accept(Object... args) throws IllegalConnectionException {
        return (R) invoke(args);
    }

}

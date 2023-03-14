package io.notcute.util.signalslot;

/**
 * A short slot with specific object, method name and unlimited argument types.
 */
public class DynamicShortSlot extends AbstractDynamicSlot implements ShortSlot {

    public DynamicShortSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    short accept(Object... args) throws IllegalConnectionException {
        return (short) invoke(args);
    }

}

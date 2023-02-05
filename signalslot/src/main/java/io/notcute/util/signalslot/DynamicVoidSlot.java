package io.notcute.util.signalslot;

/**
 * A slot with specific object, method name and unlimited argument types.
 */
public class DynamicVoidSlot extends AbstractDynamicSlot implements VoidSlot {

    public DynamicVoidSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    void accept(Object... args) throws IllegalConnectionException {
        invoke(args);
    }

}

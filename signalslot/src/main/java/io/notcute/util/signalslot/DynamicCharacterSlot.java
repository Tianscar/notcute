package io.notcute.util.signalslot;

/**
 * A char slot with specific object, method name and unlimited argument types.
 */
public class DynamicCharacterSlot extends AbstractDynamicSlot implements CharacterSlot {

    public DynamicCharacterSlot(Object obj, String methodName, Class<?>... parameterTypes) {
        super(obj, methodName, parameterTypes);
    }

    char accept(Object... args) throws IllegalConnectionException {
        return (char) invoke(args);
    }

}

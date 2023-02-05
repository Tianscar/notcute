package io.notcute.util.signalslot;

public class DynamicSignal<R> extends Signal<R> {

    /**
     * @see Signal#invoke(Object...)
     */
    public R emit(final Object... args) {
        return super.invoke(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R actuate(Slot<?> slot, Object... args) {
        if (slot instanceof DynamicSlot) return ((DynamicSlot<R>) slot).accept(args);
        else throw new IllegalArgumentException("Invalid slot type: " + slot.getClass());
    }

}

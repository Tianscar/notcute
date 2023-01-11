package a3wt.awt;

import java.lang.reflect.Field;

public class Unsafe {

    private static final sun.misc.Unsafe UNSAFE;
    static {
        sun.misc.Unsafe unsafe = null;
        try {
            @SuppressWarnings("DiscouragedPrivateApi")
            final Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        UNSAFE = unsafe;
    }

    private Unsafe(){}

    public static sun.misc.Unsafe getUnsafe() {
        return UNSAFE;
    }

}

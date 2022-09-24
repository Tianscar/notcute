package com.ansdoship.a3wt.util;

import java.lang.reflect.Field;

public class A3Unsafe {

    private static final sun.misc.Unsafe UNSAFE;
    static {
        sun.misc.Unsafe unsafe = null;
        try {
            @SuppressWarnings("DiscouragedPrivateApi")
            final Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        } catch (NoSuchFieldException ignored) {
        } catch (IllegalAccessException ignored) {
        }
        UNSAFE = unsafe;
    }

    private A3Unsafe(){}

    public static sun.misc.Unsafe getUnsafe() {
        return UNSAFE;
    }

}

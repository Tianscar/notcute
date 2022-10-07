package com.ansdoship.a3wt.util;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Arrays {
    
    private A3Arrays(){}

    public static byte[] copy(final byte[] array) {
        checkArgNotNull(array, "array");
        final byte[] result = new byte[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static short[] copy(final short[] array) {
        checkArgNotNull(array, "array");
        final short[] result = new short[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static int[] copy(final int[] array) {
        checkArgNotNull(array, "array");
        final int[] result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static long[] copy(final long[] array) {
        checkArgNotNull(array, "array");
        final long[] result = new long[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static float[] copy(final float[] array) {
        checkArgNotNull(array, "array");
        final float[] result = new float[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static double[] copy(final double[] array) {
        checkArgNotNull(array, "array");
        final double[] result = new double[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static char[] copy(final char[] array) {
        checkArgNotNull(array, "array");
        final char[] result = new char[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static boolean[] copy(final boolean[] array) {
        checkArgNotNull(array, "array");
        final boolean[] result = new boolean[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static <E> E[] copy(final E[] array) {
        checkArgNotNull(array, "array");
        final E[] result = array.clone();
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static <E extends A3Copyable<?>> E[] deepCopy(final E[] array) {
        checkArgNotNull(array, "array");
        final E[] result = array.clone();
        for (int i = 0; i < array.length; i ++) {
            result[i] = (E) array[i].copy();
        }
        return result;
    }

    public static <E extends A3Copyable<E>> E[] deepCopyStrict(final E[] array) {
        checkArgNotNull(array, "array");
        final E[] result = array.clone();
        for (int i = 0; i < array.length; i ++) {
            result[i] = array[i].copy();
        }
        return result;
    }

    public static <E> boolean contains(final E[] array, final E element) {
        checkArgNotNull(array, "array");
        for (final E e : array) {
            if (e.equals(element)) return true;
        }
        return false;
    }

    public static boolean containsIgnoreCase(final String[] array, final String element) {
        checkArgNotNull(array, "array");
        for (final String e : array) {
            if (e.equalsIgnoreCase(element)) return true;
        }
        return false;
    }
    
}

package com.ansdoship.a3wt.util;

import java.math.BigDecimal;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Arrays {
    
    private A3Arrays(){}

    public static String toPlainString(final BigDecimal[] array) {
        if (array == null)
            return "null";

        int iMax = array.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(array[i].toPlainString());
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    public static float[] double2float(final double[] array) {
        final float[] result = new float[array.length];
        for (int i = 0; i < result.length; i ++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    public static double[] float2double(final float[] array) {
        final double[] result = new double[array.length];
        for (int i = 0; i < result.length; i ++) {
            result[i] = array[i];
        }
        return result;
    }

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

    public static <E extends A3Copyable<E>> E[] deepCopy(final E[] array) {
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

    public static boolean isEmpty(final char[] array) {
        return array == null || array.length == 0;
    }
    
}

package io.notcute.util;

import java.lang.reflect.Array;
import java.util.Objects;

public final class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException();
    }

    public static int hashCode(byte[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + a[off + i];
        }

        return result;
    }

    public static int hashCode(short[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + a[off + i];
        }

        return result;
    }

    public static int hashCode(int[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + a[off + i];
        }

        return result;
    }

    public static int hashCode(long[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        long element;
        for (int i = 0; i < len; i ++) {
            element = a[off + i];
            int elementHash = (int)(element ^ (element >>> 32));
            result = 31 * result + elementHash;
        }

        return result;
    }

    public static int hashCode(float[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + Float.floatToIntBits(a[off + i]);
        }

        return result;
    }

    public static int hashCode(double[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            long bits = Double.doubleToLongBits(a[off + i]);
            result = 31 * result + (int)(bits ^ (bits >>> 32));
        }

        return result;
    }

    public static int hashCode(boolean[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + (a[off + i] ? 1231 : 1237);
        }

        return result;
    }

    public static int hashCode(char[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        for (int i = 0; i < len; i ++) {
            result = 31 * result + a[off + i];
        }

        return result;
    }

    public static int hashCode(Object[] a, int off, int len) {
        if (a == null) return 0;

        int result = 1;
        Object element;
        for (int i = 0; i < len; i ++) {
            element = a[off + i];
            result = 31 * result + (element == null ? 0 : element.hashCode());
        }

        return result;
    }

    public static int deepHashCode(Object a[], int off, int len) {
        if (a == null)
            return 0;

        int result = 1;

        Object element;
        for (int i = 0; i < len; i ++) {
            element = a[off + i];
            final int elementHash;
            final Class<?> cl;
            if (element == null)
                elementHash = 0;
            else if ((cl = element.getClass().getComponentType()) == null)
                elementHash = element.hashCode();
            else if (element instanceof Object[])
                elementHash = deepHashCode((Object[]) element, off, len);
            else
                elementHash = primitiveArrayHashCode(element, off, len, cl);

            result = 31 * result + elementHash;
        }

        return result;
    }

    private static int primitiveArrayHashCode(Object a, int off, int len, Class<?> cl) {
        return
                (cl == byte.class)    ? hashCode((byte[]) a, off, len)    :
                (cl == int.class)     ? hashCode((int[]) a, off, len)     :
                (cl == long.class)    ? hashCode((long[]) a, off, len)    :
                (cl == char.class)    ? hashCode((char[]) a, off, len)    :
                (cl == short.class)   ? hashCode((short[]) a, off, len)   :
                (cl == boolean.class) ? hashCode((boolean[]) a, off, len) :
                (cl == double.class)  ? hashCode((double[]) a, off, len)  :
                // If new primitive types are ever added, this method must be
               // expanded or we will fail here with ClassCastException.
                hashCode((float[]) a, off, len);
    }

    public static boolean contains(byte[] array, byte element) {
        if (array == null) return false;
        for (byte b : array) {
            if (b == element) return true;
        }
        return false;
    }

    public static boolean contains(short[] array, short element) {
        if (array == null) return false;
        for (short s : array) {
            if (s == element) return true;
        }
        return false;
    }

    public static boolean contains(int[] array, int element) {
        if (array == null) return false;
        for (int i : array) {
            if (i == element) return true;
        }
        return false;
    }

    public static boolean contains(long[] array, long element) {
        if (array == null) return false;
        for (long l : array) {
            if (l == element) return true;
        }
        return false;
    }

    public static boolean contains(float[] array, float element) {
        if (array == null) return false;
        for (float f : array) {
            if (f == element) return true;
        }
        return false;
    }

    public static boolean contains(double[] array, double element) {
        if (array == null) return false;
        for (double d : array) {
            if (d == element) return true;
        }
        return false;
    }

    public static boolean contains(boolean[] array, boolean element) {
        if (array == null) return false;
        for (boolean b : array) {
            if (b == element) return true;
        }
        return false;
    }

    public static boolean contains(char[] array, char element) {
        if (array == null) return false;
        for (char c : array) {
            if (c == element) return true;
        }
        return false;
    }

    public static<T> boolean contains(T[] array, T element) {
        if (array == null) return false;
        for (T t : array) {
            if (Objects.equals(t, element)) return true;
        }
        return false;
    }

    public static boolean containsIgnoreCase(String[] array, String element) {
        if (array == null) return false;
        for (String string : array) {
            if (ObjectUtils.equalsIgnoreCase(string, element)) return true;
        }
        return false;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        if (array == null) return true;
        for (Object element : array) {
            if (element != null) return false;
        }
        return true;
    }

    public static boolean deepIsEmpty(Object[] array) {
        if (array == null) return true;
        boolean result = true;
        for (Object element : array) {
            if (element instanceof byte[]) {
                result = isEmpty((byte[]) element);
            }
            else if (element instanceof short[]) {
                result = isEmpty((short[]) element);
            }
            else if (element instanceof int[]) {
                result = isEmpty((int[]) element);
            }
            else if (element instanceof long[]) {
                result = isEmpty((long[]) element);
            }
            else if (element instanceof float[]) {
                result = isEmpty((float[]) element);
            }
            else if (element instanceof double[]) {
                result = isEmpty((double[]) element);
            }
            else if (element instanceof boolean[]) {
                result = isEmpty((boolean[]) element);
            }
            else if (element instanceof char[]) {
                result = isEmpty((char[]) element);
            }
            else if (element instanceof CharSequence) {
                result = isEmpty((CharSequence) element);
            }
            else if (element instanceof Object[]) {
                result = deepIsEmpty((Object[]) element);
            }
            if (!result) break;
        }
        return result;
    }

    public static CharSequence[] copyOf(CharSequence[] original) {
        CharSequence[] copy = new CharSequence[original.length];
        for (int i = 0; i < copy.length; i ++) {
            copy[i] = TextUtils.deepCopy(original[i]);
        }
        return copy;
    }

    public static float[] copyOf(double[] original) {
        float[] copy = new float[original.length];
        for (int i = 0; i < copy.length; i ++) {
            copy[i] = (float) original[i];
        }
        return copy;
    }

    public static double[] copyOf(float[] original) {
        double[] copy = new double[original.length];
        for (int i = 0; i < copy.length; i ++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static int[] copyOf(long[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < copy.length; i ++) {
            copy[i] = (int) original[i];
        }
        return copy;
    }

    public static long[] copyOf(int[] original) {
        long[] copy = new long[original.length];
        for (int i = 0; i < copy.length; i ++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static CharSequence[] copyOf(CharSequence[] original, int newLength) {
        CharSequence[] copy = new CharSequence[newLength];
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = TextUtils.deepCopy(original[i]);
        }
        return copy;
    }

    public static float[] copyOf(double[] original, int newLength) {
        float[] copy = new float[newLength];
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = (float) original[i];
        }
        return copy;
    }

    public static double[] copyOf(float[] original, int newLength) {
        double[] copy = new double[newLength];
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static int[] copyOf(long[] original, int newLength) {
        int[] copy = new int[newLength];
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = (int) original[i];
        }
        return copy;
    }

    public static long[] copyOf(int[] original, int newLength) {
        long[] copy = new long[newLength];
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = original[i];
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    public static<T extends Cloneable> T[] deepCopyOf(T[] original, int newLength) {
        if (original == null) return null;
        T[] copy = (T[]) Array.newInstance(original.getClass(), newLength);
        for (int i = 0, len = Math.min(original.length, newLength); i < len; i ++) {
            copy[i] = (T) original[i].clone();
        }
        return copy;
    }

    public static float[] copyOfRange(double[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        float[] copy = new float[newLength];
        for (int i = 0, len = Math.min(original.length - from, newLength); i < len; i ++) {
            copy[i] = (float) original[from + i];
        }
        return copy;
    }

    public static double[] copyOfRange(float[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        double[] copy = new double[newLength];
        for (int i = 0, len = Math.min(original.length - from, newLength); i < len; i ++) {
            copy[i] = original[from + i];
        }
        return copy;
    }

    public static int[] copyOfRange(long[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int[] copy = new int[newLength];
        for (int i = 0, len = Math.min(original.length - from, newLength); i < len; i ++) {
            copy[i] = (int) original[from + i];
        }
        return copy;
    }

    public static long[] copyOfRange(int[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        long[] copy = new long[newLength];
        for (int i = 0, len = Math.min(original.length - from, newLength); i < len; i ++) {
            copy[i] = original[from + i];
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    public static<T extends Cloneable> T[] deepCopyOfRange(T[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        if (original == null) return null;
        T[] copy = (T[]) Array.newInstance(original.getClass(), newLength);
        for (int i = 0, len = Math.min(original.length - from, newLength); i < len; i ++) {
            copy[i] = (T) original[from + i].clone();
        }
        return copy;
    }

}

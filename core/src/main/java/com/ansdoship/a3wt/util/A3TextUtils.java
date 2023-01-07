package com.ansdoship.a3wt.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3TextUtils {

    private A3TextUtils(){}

    public static char[] getChars(final CharSequence text) {
        return getChars(text, 0, text.length());
    }

    public static char[] getChars(final CharSequence text, final int offset, final int length) {
        if (text == null) return null;
        final char[] chars = new char[length];
        for (int i = 0; i < length; i ++) {
            chars[i] = text.charAt(offset + i);
        }
        return chars;
    }

    public static List<CharSequence> splitLines(final CharSequence text) {
        checkArgNotEmpty(text, "text");
        final List<CharSequence> lines = new ArrayList<>();
        int index = 0;
        for (int i = 0, length = text.length(); i < length; i ++) {
            if (text.charAt(i) == '\n') {
                lines.add(text.subSequence(index, i));
                if (i + 1 <= text.length()) index = i + 1;
            }
        }
        lines.add(text.subSequence(index, text.length()));
        return lines;
    }

    public static List<char[]> splitLineChars(final CharSequence text) {
        checkArgNotEmpty(text, "text");
        final List<char[]> lines = new ArrayList<>();
        int index = 0;
        for (int i = 0, length = text.length(); i < length; i ++) {
            if (text.charAt(i) == '\n') {
                char[] line = new char[i - index];
                for (int j = 0; j < line.length; j ++) {
                    line[j] = text.charAt(index + j);
                }
                lines.add(line);
                if (i + 1 < text.length()) index = i + 1;
            }
        }
        char[] line = new char[text.length() - index];
        for (int j = 0; j < line.length; j ++) {
            line[j] = text.charAt(index + j);
        }
        lines.add(line);
        return lines;
    }

    public static boolean isEmpty(final CharSequence text) {
        return text == null || text.length() < 1;
    }

    public static byte[] parseByteArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final byte[] result = new byte[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Byte.parseByte(strings[i]);
        }
        return result;
    }

    public static short[] parseShortArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final short[] result = new short[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Short.parseShort(strings[i]);
        }
        return result;
    }

    public static int[] parseIntArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }

    public static long[] parseLongArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final long[] result = new long[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Long.parseLong(strings[i]);
        }
        return result;
    }

    public static float[] parseFloatArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final float[] result = new float[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Float.parseFloat(strings[i]);
        }
        return result;
    }

    public static double[] parseDoubleArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final double[] result = new double[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Double.parseDouble(strings[i]);
        }
        return result;
    }

    public static boolean[] parseBooleanArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final boolean[] result = new boolean[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = Boolean.parseBoolean(strings[i]);
        }
        return result;
    }

    public static char[] parseCharArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final char[] result = new char[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = strings[i].charAt(0);
        }
        return result;
    }

    public static String[] parseStringArray(final String array) {
        checkArgNotNull(array, "array");
        final String arrayTrimmed = array.trim();
        if (arrayTrimmed.equalsIgnoreCase("null")) return null;
        if (arrayTrimmed.length() < 2 || arrayTrimmed.charAt(0) != '[' || arrayTrimmed.charAt(arrayTrimmed.length() - 1) != ']')
            throw new IllegalArgumentException("Invalid array string: " + array);
        return arrayTrimmed.substring(1, arrayTrimmed.length() - 1).split(",");
    }

    public static BigInteger[] parseBigIntegerArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final BigInteger[] result = new BigInteger[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = new BigInteger(strings[i]);
        }
        return result;
    }

    public static BigDecimal[] parseBigDecimalArray(final String array) {
        final String[] strings = parseStringArray(array);
        if (strings == null) return null;
        final BigDecimal[] result = new BigDecimal[strings.length];
        for (int i = 0; i < strings.length; i ++) {
            result[i] = new BigDecimal(strings[i]);
        }
        return result;
    }

}

package a3wt.util;

import java.util.Collection;

public class A3Preconditions {

    private A3Preconditions(){}

    public static void checkArgArrayLength(final long[] arg, final int length) {
        checkArgNotNull(arg, "arg");
        if (arg.length != length) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLengthMin(final long[] arg, final int min, final boolean allowEquals) {
        checkArgNotNull(arg, "arg");
        if (allowEquals) {
            if (arg.length < min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
        }
        else if (arg.length <= min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLength(final int[] arg, final int length) {
        checkArgNotNull(arg, "arg");
        if (arg.length != length) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLengthMin(final int[] arg, final int min, final boolean allowEquals) {
        checkArgNotNull(arg, "arg");
        if (allowEquals) {
            if (arg.length < min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
        }
        else if (arg.length <= min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLength(final double[] arg, final int length) {
        checkArgNotNull(arg, "arg");
        if (arg.length != length) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLengthMin(final double[] arg, final int min, final boolean allowEquals) {
        checkArgNotNull(arg, "arg");
        if (allowEquals) {
            if (arg.length < min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
        }
        else if (arg.length <= min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLength(final float[] arg, final int length) {
        checkArgNotNull(arg, "arg");
        if (arg.length != length) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgArrayLengthMin(final float[] arg, final int min, final boolean allowEquals) {
        checkArgNotNull(arg, "arg");
        if (allowEquals) {
            if (arg.length < min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
        }
        else if (arg.length <= min) throw new IllegalArgumentException("Invalid array length: " + arg.length);
    }

    public static void checkArgRangeStartEnd(final long start, final long end) {
        if (end < start) throw new IllegalArgumentException(start + " (start) > " + end + "(end) !");
    }

    public static void checkArgRangeStartEnd(final double start, final double end) {
        if (end < start) throw new IllegalArgumentException(start + " (start) > " + end + "(end) !");
    }

    public static void checkArgRangeLeftRight(final long left, final long right) {
        if (right < left) throw new IllegalArgumentException(left + " (left) > " + right + "(right) !");
    }

    public static void checkArgRangeLeftRight(final double left, final double right) {
        if (right < left) throw new IllegalArgumentException(left + " (left) > " + right + "(right) !");
    }

    public static void checkArgRangeTopBottom(final long top, final long bottom) {
        if (bottom < top) throw new IllegalArgumentException(top + " (top) > " + bottom + "(bottom) !");
    }

    public static void checkArgRangeTopBottom(final double top, final double bottom) {
        if (bottom < top) throw new IllegalArgumentException(top + " (top) > " + bottom + "(bottom) !");
    }

    public static void checkArgRangeBounds(final long left, final long top, final long right, final long bottom) {
        checkArgRangeLeftRight(left, right);
        checkArgRangeTopBottom(top, bottom);
    }

    public static void checkArgRangeBounds(final double left, final double top, final double right, final double bottom) {
        checkArgRangeLeftRight(left, right);
        checkArgRangeTopBottom(top, bottom);
    }

    public static void checkArgRangeMin(final long arg, final long min, final boolean allowEquals, final String msg) {
        if (!allowEquals) if (arg < min) throw msg == null ?
                new IllegalArgumentException(arg + "<" + min + "!") : new IllegalArgumentException(msg + " < " + min + "!");
        else if (arg <= min) throw msg == null ?
                new IllegalArgumentException(arg + " <= " + min + "!") : new IllegalArgumentException(msg + " <= " + min + "!");
    }

    public static void checkArgRangeMin(final long arg, final long min, final boolean allowEquals) {
        checkArgRangeMin(arg, min, allowEquals, null);
    }

    public static void checkArgRangeMax(final long arg, final long max, final boolean allowEquals, final String msg) {
        if (!allowEquals) if (arg > max) throw msg == null ?
                new IllegalArgumentException(arg + ">" + max + "!") : new IllegalArgumentException(msg + " > " + max + "!");
        else if (arg >= max) throw msg == null ?
                new IllegalArgumentException(arg + " >= " + max + "!") : new IllegalArgumentException(msg + " >= " + max + "!");
    }

    public static void checkArgRangeMax(final long arg, final long max, final boolean allowEquals) {
        checkArgRangeMax(arg, max, allowEquals, null);
    }

    public static void checkArgRange(final long arg, final long min, final long max, final boolean allowEquals, final String msg) {
        checkArgRangeMin(arg, min, allowEquals, msg);
        checkArgRangeMax(arg, max, allowEquals, msg);
    }

    public static void checkArgRange(final long arg, final long min, final long max, final boolean allowEquals) {
        checkArgRange(arg, min, max, allowEquals, null);
    }

    public static void checkArgNotNull(final Object arg) {
        checkArgNotNull(arg, null);
    }

    public static void checkArgNotNull(final Object arg, final String msg) {
        if (arg == null) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is null!");
        }
    }

    public static void checkArgNotEmpty(final CharSequence arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final CharSequence arg, final String msg) {
        if (arg == null || arg.length() < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final Collection<?> arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final Collection<?> arg, final String msg) {
        if (arg == null || arg.isEmpty()) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final Object[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final Object[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final byte[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final byte[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final short[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final short[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final int[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final int[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final long[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final long[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final float[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final float[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final double[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final double[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final char[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final char[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(final boolean[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final boolean[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgElementsNotNull(final Collection<?> arg) {
        checkArgElementsNotNull(arg, null);
    }

    public static void checkArgElementsNotNull(final Collection<?> arg, final String msg) {
        if (arg == null || arg.isEmpty()) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
        else {
            int i = 0;
            for (Object o : arg) {
                i ++;
                if (o == null) {
                    if (msg == null) throw new IllegalArgumentException();
                    else throw new IllegalArgumentException(msg + " has null element at index: " + i + "!");
                }
            }
        }
    }

    public static void checkArgElementsNotNull(final Object[] arg) {
        checkArgElementsNotNull(arg, null);
    }

    public static void checkArgElementsNotNull(final Object[] arg, final String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
        else {
            for (int i = 0; i < arg.length; i ++) {
                if (arg[i] == null) {
                    if (msg == null) throw new IllegalArgumentException();
                    else throw new IllegalArgumentException(msg + " has null element at index: " + i + "!");
                }
            }
        }
    }

}

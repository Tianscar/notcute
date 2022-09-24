package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Platform;

import java.util.Collection;

public class A3Asserts {
    
    private A3Asserts(){}

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
    
    public static void checkNotNull(final Object object) {
        checkNotNull(object, null);
    }
    
    public static void checkNotNull(final Object object, final String msg) {
        if (object == null) {
            if (msg == null) throw new NullPointerException();
            else throw new NullPointerException(msg + " is null!");
        }
    }

    public static void checkNotEmpty(final CharSequence arg) {
        checkNotEmpty(arg, null);
    }

    public static void checkNotEmpty(final CharSequence arg, final String msg) {
        checkNotNull(arg, msg);
        if (arg.length() < 1) {
            if (msg == null) throw new IllegalStateException();
            else throw new IllegalStateException(msg + " is empty!");
        }
    }

    public static void checkNotEmpty(final Collection<?> arg) {
        checkNotEmpty(arg, null);
    }

    public static void checkNotEmpty(final Collection<?> arg, final String msg) {
        checkNotNull(arg, msg);
        if (arg.isEmpty()) {
            if (msg == null) throw new IllegalStateException();
            else throw new IllegalStateException(msg + " is empty!");
        }
    }

    public static void checkNotEmpty(final Object[] arg) {
        checkNotEmpty(arg, null);
    }

    public static void checkNotEmpty(final Object[] arg, final String msg) {
        checkNotNull(arg, msg);
        if (arg.length < 1) {
            if (msg == null) throw new IllegalStateException();
            else throw new IllegalStateException(msg + " is empty!");
        }
    }

    public static void notSupportedYet(final String msg) {
        throw new UnsupportedOperationException(msg + " not supported yet!");
    }

    public static void notSupportedYetOnPlatform(final A3Platform platform, final String msg) {
        checkArgNotNull(platform, "platform");
        throw new UnsupportedOperationException(msg + " not supported yet on " + platform.getBackendName() + "!");
    }
    
}

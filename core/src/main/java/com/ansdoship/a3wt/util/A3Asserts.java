package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Platform;

import java.util.Collection;

public class A3Asserts {
    
    private A3Asserts(){}
    
    public static void checkArgNotNull(final Object arg) {
        checkArgNotNull(arg, null);
    }
    
    public static void checkArgNotNull(final Object arg, final String msg) {
        if (arg == null) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is null!");
        }
    }

    public static void checkArgNotEmpty(final String arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(final String arg, final String msg) {
        if (arg == null || arg.isEmpty()) {
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

    public static void notSupportedYet(final String msg) {
        throw new UnsupportedOperationException(msg + " not supported yet!");
    }

    public static void notSupportedYetOnPlatform(final A3Platform platform, final String msg) {
        checkArgNotNull(platform, "platform");
        throw new UnsupportedOperationException(msg + " not supported yet on " + platform.getBackendName() + "!");
    }
    
}

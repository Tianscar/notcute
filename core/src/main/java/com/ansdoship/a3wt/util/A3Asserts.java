package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.A3WT;
import com.ansdoship.a3wt.app.A3Platform;

import java.util.Collection;

public class A3Asserts {
    
    private A3Asserts(){}
    
    public static void checkArgNotNull(Object arg) {
        checkArgNotNull(arg, null);
    }
    
    public static void checkArgNotNull(Object arg, String msg) {
        if (arg == null) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is null!");
        }
    }

    public static void checkArgNotEmpty(String arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(String arg, String msg) {
        if (arg == null || arg.isEmpty()) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(Collection<?> arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(Collection<?> arg, String msg) {
        if (arg == null || arg.isEmpty()) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }

    public static void checkArgNotEmpty(Object[] arg) {
        checkArgNotEmpty(arg, null);
    }

    public static void checkArgNotEmpty(Object[] arg, String msg) {
        if (arg == null || arg.length < 1) {
            if (msg == null) throw new IllegalArgumentException();
            else throw new IllegalArgumentException(msg + " is empty!");
        }
    }
    
    public static void checkNotNull(Object object) {
        checkNotNull(object, null);
    }
    
    public static void checkNotNull(Object object, String msg) {
        if (object == null) {
            if (msg == null) throw new NullPointerException();
            else throw new NullPointerException(msg + " is null!");
        }
    }

    public static void notSupportedYet(String msg) {
        throw new UnsupportedOperationException(msg + " not supported yet!");
    }

    public static void notSupportedYetOnPlatform(String msg) {
        notSupportedYetOnPlatform(A3WT.getPlatform(), msg);
    }

    public static void notSupportedYetOnPlatform(A3Platform platform, String msg) {
        checkArgNotNull(platform, "platform");
        throw new UnsupportedOperationException(msg + " not supported yet on " + platform.getBackendName() + "!");
    }
    
}

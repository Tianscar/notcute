package a3wt.util;

import a3wt.app.A3Platform;

import java.util.Collection;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Asserts {
    
    private A3Asserts(){}
    
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

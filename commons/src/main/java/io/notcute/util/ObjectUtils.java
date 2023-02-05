package io.notcute.util;

public final class ObjectUtils {

    private ObjectUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null || b == null) return false;
        else return a.equalsIgnoreCase(b);
    }

}

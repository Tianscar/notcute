package io.notcute.util;

public final class TextUtils {

    private TextUtils() {
        throw new UnsupportedOperationException();
    }

    public static char[] getChars(CharSequence text, int offset, int length) {
        if (text == null) return null;
        char[] chars = new char[length];
        for (int i = 0; i < length; i ++) {
            chars[i] = text.charAt(offset + i);
        }
        return chars;
    }

    public static CharSequence deepCopy(CharSequence text) {
        if (text == null) return null;
        else return text.subSequence(0, text.length());
    }

}

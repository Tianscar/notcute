package com.ansdoship.a3wt.util;

import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;

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
                if (i + 1 < text.length()) index = i + 1;
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

}

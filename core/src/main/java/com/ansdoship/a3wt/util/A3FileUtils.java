package com.ansdoship.a3wt.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class A3FileUtils {

    private A3FileUtils(){}

    private static final int BUFFER_SIZE = 8192;

    public static boolean createFileIfNotExist(File file) {
        try {
            if (file.exists() && file.isFile()) return true;
            else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) return false;
                }
                return file.createNewFile();
            }
        }
        catch (IOException ignored) {
        }
        return false;
    }

    public static boolean createDirIfNotExist(File dir) {
        if (dir.exists()) {
            return dir.isDirectory();
        }
        else return dir.mkdirs();
    }

    public static void transferTo(InputStream source, OutputStream target) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = source.read(buffer)) != -1) {
            target.write(buffer, 0, length);
        }
    }

    public static boolean isStartsWithSeparator(String path) {
        return path.startsWith("\\") || path.startsWith("/");
    }

    public static boolean isEndsWithSeparator(String path) {
        return path.endsWith("\\") || path.endsWith("/");
    }

    public static String normalizeSeparatorsUNIX(String path) {
        return path.replaceAll("\\\\", "/");
    }

    public static String normalizeSeparatorsDOS(String path) {
        return path.replaceAll("/", "\\");
    }

    public static String normalizeSeparators(String path) {
        return normalizeSeparatorsUNIX(path).replaceAll("/", File.separator);
    }

    public static String removeStartSeparator(String path) {
        if (isStartsWithSeparator(path)) return path.substring(1);
        else return path;
    }

    public static String removeEndSeparator(String path) {
        if (isEndsWithSeparator(path)) return path.substring(0, path.length() - 1);
        else return path;
    }

    public static String ensureStartSeparatorUNIX(String path) {
        if (isStartsWithSeparator(path)) return path;
        else return "/" + path;
    }

    public static String ensureStartSeparatorDOS(String path) {
        if (isStartsWithSeparator(path)) return path;
        else return "\\" + path;
    }

    public static String ensureStartSeparator(String path) {
        if (isStartsWithSeparator(path)) return path;
        else return File.separator + path;
    }

    public static String ensureEndSeparatorUNIX(String path) {
        if (isEndsWithSeparator(path)) return path;
        else return path + "/";
    }

    public static String ensureEndSeparatorDOS(String path) {
        if (isEndsWithSeparator(path)) return path;
        else return path + "\\";
    }

    public static String ensureEndSeparator(String path) {
        if (isEndsWithSeparator(path)) return path;
        else return path + File.separator;
    }

}

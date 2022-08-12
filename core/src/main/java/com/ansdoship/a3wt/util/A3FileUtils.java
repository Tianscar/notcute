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
            if (file.exists()) return true;
            else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) return false;
                }
                return file.createNewFile();
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    public static void transferTo(InputStream source, OutputStream target) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = source.read(buffer)) != -1) {
            target.write(buffer, 0, length);
        }
    }

}

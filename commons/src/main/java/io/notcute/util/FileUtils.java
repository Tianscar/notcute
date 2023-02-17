package io.notcute.util;

import java.io.File;
import java.net.URI;
import java.util.Objects;

public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean createDirIfNotExist(File dir) {
        Objects.requireNonNull(dir);
        if (dir.exists()) {
            return dir.isDirectory();
        }
        else return dir.mkdirs();
    }

    public static String removeStartSeparator(String text) {
        return text.charAt(0) == File.separatorChar ? text.substring(1) : text;
    }

    public static URI[] files2URIs(final File[] files) {
        final URI[] uris = new URI[files.length];
        for (int i = 0; i < uris.length; i ++) {
            uris[i] = files[i].toURI();
        }
        return uris;
    }

}

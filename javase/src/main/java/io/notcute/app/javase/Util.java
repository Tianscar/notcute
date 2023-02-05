package io.notcute.app.javase;

import java.io.File;
import java.util.Objects;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static boolean createDirIfNotExist(File dir) {
        Objects.requireNonNull(dir);
        if (dir.exists()) {
            return dir.isDirectory();
        }
        else return dir.mkdirs();
    }

}

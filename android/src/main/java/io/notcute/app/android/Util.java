package io.notcute.app.android;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static boolean deleteSharedPreferences(Context context, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.deleteSharedPreferences(name);
        } else {
            context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().commit();
            return new File(new File(context.getApplicationInfo().dataDir, "shared_prefs"), name + ".xml").delete();
        }
    }

    public static File getSharedPreferencesDir(Context context) {
        return new File(context.getApplicationInfo().dataDir, "shared_prefs");
    }

    public static boolean isExternalStorageWriteable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean createDirIfNotExist(final File dir) {
        if (dir.exists()) {
            return dir.isDirectory();
        }
        else return dir.mkdirs();
    }

    public static File getStorageDir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.getStorageDirectory();
        }
        else {
            return isExternalStorageWriteable() ? Environment.getExternalStorageDirectory() : Environment.getDataDirectory();
        }
    }

}

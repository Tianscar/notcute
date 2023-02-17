package io.notcute.internal.android;

import android.annotation.SuppressLint;
import android.content.Context;

public final class AndroidShared {

    private AndroidShared() {
        throw new UnsupportedOperationException();
    }

    @SuppressLint("StaticFieldLeak")
    private static volatile Context context = null;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AndroidShared.context = context;
    }

    public static boolean hasContext() {
        return context != null;
    }

    public static void releaseContext() {
        context = null;
    }

}

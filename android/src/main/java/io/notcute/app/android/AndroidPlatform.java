package io.notcute.app.android;

import android.os.Build;
import io.notcute.app.Platform;

public class AndroidPlatform implements Platform {

    public static final String BACKEND_NAME = "Android SDK";
    public static final int BASELINE_DPI = 160;

    @Override
    public String getBackendName() {
        return BACKEND_NAME;
    }

    @Override
    public String getPlatformName() {
        return Build.PRODUCT;
    }

    @Override
    public String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }

    @Override
    public String getPlatformArch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS[0];
        }
        else return Build.CPU_ABI;
    }

    @Override
    public int getBaselineDPI() {
        return BASELINE_DPI;
    }

    @Override
    public boolean isHeadless() {
        return false;
    }

}

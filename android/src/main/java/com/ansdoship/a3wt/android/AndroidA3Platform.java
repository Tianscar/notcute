package com.ansdoship.a3wt.android;

import android.os.Build;
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.util.A3Logger;

public class AndroidA3Platform implements A3Platform {

    protected static final AndroidA3Logger logger = new AndroidA3Logger();

    public static final String BACKEND_NAME = "Android GUI";

    public static final int BASELINE_PPI = 160;

    @Override
    public A3Logger getLogger() {
        return logger;
    }

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
    public int getBaselinePPI() {
        return BASELINE_PPI;
    }

}

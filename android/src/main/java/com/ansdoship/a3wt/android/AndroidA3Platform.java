package com.ansdoship.a3wt.android;

import android.os.Build;
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.util.A3I18NBundle;
import com.ansdoship.a3wt.util.A3Logger;
import com.ansdoship.a3wt.util.DefaultA3I18NBundle;

public class AndroidA3Platform implements A3Platform {

    protected static final AndroidA3Logger logger = new AndroidA3Logger();
    protected static final AndroidA3GraphicsKit graphicsKit = new AndroidA3GraphicsKit();
    protected static final DefaultA3I18NBundle i18NBundle = new DefaultA3I18NBundle();

    public static final String BACKEND_NAME = "Android Native GUI";

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
    public A3I18NBundle getI18NBundle() {
        return i18NBundle;
    }

    @Override
    public int getBaselinePPI() {
        return BASELINE_PPI;
    }

}

package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.A3WT;
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.util.A3Logger;

public class AWTA3Platform implements A3Platform {

    protected static volatile AWTA3Logger logger = new AWTA3Logger();
    protected static volatile AWTA3GraphicsKit graphicsKit = new AWTA3GraphicsKit();

    public static final String BACKEND_NAME = "AWT";

    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String OS_ARCH = System.getProperty("os.arch");

    static {
        A3WT.setPlatform(new AWTA3Platform());
    }

    @Override
    public A3Logger getLogger() {
        return logger;
    }

    @Override
    public A3GraphicsKit getGraphicsKit() {
        return graphicsKit;
    }

    @Override
    public String getBackendName() {
        return BACKEND_NAME;
    }

    @Override
    public String getPlatformName() {
        return OS_NAME;
    }

    @Override
    public String getPlatformVersion() {
        return OS_VERSION;
    }

    @Override
    public String getPlatformArch() {
        return OS_ARCH;
    }

}

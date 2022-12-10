package com.ansdoship.a3wt.app;

import com.ansdoship.a3wt.util.A3Logger;

public interface A3Platform {

    A3Logger getLogger();

    String getBackendName();
    String getPlatformName();
    String getPlatformVersion();
    String getPlatformArch();

    int getBaselinePPI();

}

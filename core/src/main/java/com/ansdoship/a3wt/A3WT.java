package com.ansdoship.a3wt;

import com.ansdoship.a3wt.app.A3Platform;

public final class A3WT {

    private A3WT(){}

    private static volatile A3Platform platform;

    public static void setPlatform(A3Platform platform) {
        A3WT.platform = platform;
    }

    public static A3Platform getPlatform() {
        return platform;
    }

}

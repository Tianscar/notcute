package io.notcute.app;

public interface Platform {

    String getBackendName();
    String getPlatformName();
    String getPlatformVersion();
    String getPlatformArch();

    int getBaselineDPI();

    boolean isHeadless();

}

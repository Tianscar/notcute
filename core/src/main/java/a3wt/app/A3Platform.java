package a3wt.app;

public interface A3Platform {

    String getBackendName();
    String getPlatformName();
    String getPlatformVersion();
    String getPlatformArch();

    int getBaselinePPI();

}

package io.notcute.app.javase;

import io.notcute.app.Platform;

public class JavaSEPlatform implements Platform {

    public static final String BACKEND_NAME = "Java SE";
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String OS_ARCH = System.getProperty("os.arch");

    public static final int BASELINE_DPI = 96;

    public static final boolean isWindows, isMac, isLinux, isSolaris, isAix;
    public static final boolean isUnix, isX11;
    static {
        final String os = OS_NAME.trim().toLowerCase();
        isWindows = os.contains("win");
        isMac = os.contains("mac") || os.contains("osx");
        isLinux = os.contains("nux");
        isSolaris = os.contains("sunos") || os.contains("solaris");
        isAix = os.contains("aix");
        isUnix = !isWindows;
        isX11 = !isWindows && !isMac;
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

    @Override
    public int getBaselineDPI() {
        return BASELINE_DPI;
    }

    @Override
    public boolean isHeadless() {
        return true;
    }

    public static final boolean isJava9;
    static {
        boolean hasModule;
        try {
            Class.forName("java.lang.Module");
            hasModule = true;
        }
        catch (ClassNotFoundException e) {
            hasModule = false;
        }
        isJava9 = hasModule;
    }

}

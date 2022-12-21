package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.util.A3Logger;

public class AWTA3Platform implements A3Platform {

    protected static final AWTA3Logger logger = new AWTA3Logger();

    public static final String BACKEND_NAME = "AWT (Java2D)";

    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String OS_ARCH = System.getProperty("os.arch");

    private static final boolean isWindows, isMac, isLinux, isSolaris, isAix;
    private static final boolean isUnix, isX11;
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

    public static boolean isWindows() {
        return isWindows;
    }

    public static boolean isMac() {
        return isMac;
    }

    public static boolean isLinux() {
        return isLinux;
    }

    public static boolean isSolaris() {
        return isSolaris;
    }

    public static boolean isAix() {
        return isAix;
    }

    public static boolean isUnix() {
        return isUnix;
    }

    public static boolean isX11() {
        return isX11;
    }

    public static final int BASELINE_PPI = 96;

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
    public int getBaselinePPI() {
        return BASELINE_PPI;
    }

}

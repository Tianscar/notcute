package io.notcute.app.awt;

import io.notcute.app.javase.JavaSEManifest;

public final class AWTManifest {

    private AWTManifest() {
        throw new UnsupportedOperationException();
    }

    public static class Config extends JavaSEManifest.Config {
        public Config() {
            super();
        }
        public Config(String organizationName, String applicationName) {
            super(organizationName, applicationName);
        }
    }

    public static void apply() {
        apply(null);
    }

    private static final double java2DUIScaleFactor;
    private static final boolean java2DUIScaleDefined;
    static {
        double factor = mGetJava2DUIScaleFactor();
        if (factor == -1) {
            java2DUIScaleDefined = false;
            java2DUIScaleFactor = 1.0;
        }
        else {
            java2DUIScaleDefined = true;
            java2DUIScaleFactor = factor;
        }
    }
    private static double mGetJava2DUIScaleFactor() {
        String scaleFactor = System.getProperty("sun.java2d.uiScale", "-1");
        if (scaleFactor == null || "-1".equals(scaleFactor)) return -1;
        try {
            double unit = 1;
            if (scaleFactor.endsWith("x")) {
                scaleFactor = scaleFactor.substring(0, scaleFactor.length() - 1);
            }
            else if (scaleFactor.endsWith("dpi")) {
                unit = 96;
                scaleFactor = scaleFactor.substring(0, scaleFactor.length() - 3);
            }
            else if (scaleFactor.endsWith("%")) {
                unit = 100;
                scaleFactor = scaleFactor.substring(0, scaleFactor.length() - 1);
            }
            double scale = Double.parseDouble(scaleFactor);
            return scale <= 0 ? -1 : scale / unit;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static double getJava2DUIScaleFactor() {
        return java2DUIScaleFactor;
    }

    public static boolean isJava2DUIScaleDefined() {
        return java2DUIScaleDefined;
    }

    private static final boolean isUIScaleEnabled = "true".equals(System.getProperty("sun.java2d.uiScale.enabled", "true"));
    public static boolean isUIScaleEnabled() {
        return isUIScaleEnabled;
    }

    public static void apply(Config config) {
        if (config == null) config = new Config();
        JavaSEManifest.apply(config);
        if (AWTPlatform.isX11) System.setProperty("sun.java2d.uiScale", "1.0");
    }

}

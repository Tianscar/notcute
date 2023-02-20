package io.notcute.app.awt;

import io.notcute.app.javase.JavaSEPlatform;

import java.awt.GraphicsEnvironment;

public class AWTPlatform extends JavaSEPlatform {

    public static final String BACKEND_NAME = "AWT (Java2D)";
    public static final String BACKEND_NAME_HEADLESS = "AWT (Java2D) Headless";

    public static final int BASELINE_SPI = 72; // For AWT's font render engine, points are DPI 72

    @Override
    public String getBackendName() {
        return isHeadless() ? BACKEND_NAME_HEADLESS : BACKEND_NAME;
    }

    @Override
    public boolean isHeadless() {
        return GraphicsEnvironment.isHeadless();
    }

}

package io.notcute.app.swing;

import io.notcute.app.awt.AWTPlatform;

import java.awt.GraphicsEnvironment;

public class SwingPlatform extends AWTPlatform {

    public static final String BACKEND_NAME = "Swing (Java2D)";
    public static final String BACKEND_NAME_HEADLESS = "Swing (Java2D) Headless";

    @Override
    public String getBackendName() {
        return isHeadless() ? BACKEND_NAME_HEADLESS : BACKEND_NAME;
    }

    @Override
    public boolean isHeadless() {
        return GraphicsEnvironment.isHeadless();
    }

}

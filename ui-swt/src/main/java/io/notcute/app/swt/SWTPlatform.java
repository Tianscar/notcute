package io.notcute.app.swt;

import io.notcute.app.javase.JavaSEPlatform;

public class SWTPlatform extends JavaSEPlatform {

    public static final String BACKEND_NAME = "SWT";

    @Override
    public String getBackendName() {
        return BACKEND_NAME;
    }

    @Override
    public boolean isHeadless() {
        return false;
    }

}

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

    public static void apply(Config config) {
        if (config == null) config = new Config();
        JavaSEManifest.apply(config);
        if (AWTPlatform.isJava9) System.setProperty("sun.java2d.uiScale.enabled", "false");
    }

}

package io.notcute.app.awt;

import io.notcute.app.javase.JavaSEManifest;
import io.notcute.internal.awt.Desktop;
import io.notcute.util.QuietCallable;

public final class AWTManifest {

    private AWTManifest() {
        throw new UnsupportedOperationException();
    }

    public static class Config extends JavaSEManifest.Config {

        public Runnable getAboutHandler() {
            return aboutHandler;
        }

        public void setAboutHandler(Runnable aboutHandler) {
            this.aboutHandler = aboutHandler;
        }

        public Runnable getPreferencesHandler() {
            return preferencesHandler;
        }

        public void setPreferencesHandler(Runnable preferencesHandler) {
            this.preferencesHandler = preferencesHandler;
        }

        public QuietCallable<Boolean> getQuitHandler() {
            return quitHandler;
        }

        public void setQuitHandler(QuietCallable<Boolean> quitHandler) {
            this.quitHandler = quitHandler;
        }

        private Runnable aboutHandler = null;
        private Runnable preferencesHandler = null;
        private QuietCallable<Boolean> quitHandler = null;

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
        if (AWTPlatform.isMac) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.appearance", "system");
            System.setProperty("apple.awt.fullscreenable", "true");
            String applicationName = config.getApplicationName();
            if (applicationName != null) System.setProperty("apple.awt.application.name", applicationName);
        }
        else if (AWTPlatform.isX11) {
            System.setProperty("jdk.gtk.version", "3");
        }
        if (AWTPlatform.isJava9) System.setProperty("sun.java2d.uiScale.enabled", "false");
        if (config.aboutHandler != null) Desktop.setAboutHandler(config.aboutHandler);
        if (config.preferencesHandler != null) Desktop.setPreferencesHandler(config.preferencesHandler);
        QuietCallable<Boolean> quitHandler = config.quitHandler;
        if (quitHandler != null) Desktop.setQuitHandler(quitResponse -> {
            if (quitHandler.call()) quitResponse.performQuit();
            else quitResponse.cancelQuit();
        });
    }

}

package io.notcute.app.javase;

import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

public final class JavaSEManifest {

    private JavaSEManifest() {
        throw new UnsupportedOperationException();
    }

    public static class Config implements Resetable, SwapCloneable {

        public Config() {
        }

        public Config(String organizationName, String applicationName) {
            setOrganizationName(organizationName);
            setApplicationName(applicationName);
        }

        public void setConfig(Config config) {
            setOrganizationName(config.getOrganizationName());
            setApplicationName(config.getApplicationName());
        }

        private volatile String organizationName = null;

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        private volatile String applicationName = null;
        @Override
        public Object clone() {
            try {
                return super.clone();
            }
            catch (CloneNotSupportedException e) {
                return new Config(getOrganizationName(), getApplicationName());
            }
        }

        @Override
        public void from(Object src) {
            setConfig((Config) src);
        }

        @Override
        public void reset() {
            organizationName = applicationName = null;
        }

    }

    public static void apply() {
        apply(null);
    }

    public static void apply(Config config) {
        if (config == null) config = new Config();
        if (config.getOrganizationName() != null) System.setProperty("io.notcute.app.javase.organizationname", config.getOrganizationName());
        if (config.getApplicationName() != null) System.setProperty("io.notcute.app.javase.applicationname", config.getApplicationName());
    }

}

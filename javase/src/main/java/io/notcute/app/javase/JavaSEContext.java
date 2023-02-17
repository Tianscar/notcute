package io.notcute.app.javase;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.Preferences;
import io.notcute.context.Context;
import io.notcute.context.Identifier;
import io.notcute.context.Producer;
import io.notcute.util.signalslot.Dispatcher;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class JavaSEContext implements Context {

    public static final File HOME = new File(System.getProperty("user.home"));
    public static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));
    public static final File BASE_CACHE_DIR;
    public static final File BASE_CONFIG_DIR;
    public static final File BASE_FILES_DIR;
    static {
        if (JavaSEPlatform.isWindows) {
            String APPDATA = System.getenv("APPDATA");
            String LOCALAPPDATA = System.getenv("LOCALAPPDATA");
            File Local;
            File Roaming;
            if (LOCALAPPDATA == null) {
                if (Float.parseFloat(JavaSEPlatform.OS_VERSION) < 6.0) {
                    if (APPDATA == null) Local = new File(HOME, "Application Data");
                    else Local = new File(APPDATA);
                }
                else Local = new File(HOME, "AppData\\Local");
            }
            else Local = new File(LOCALAPPDATA);
            if (APPDATA == null) {
                if (Float.parseFloat(JavaSEPlatform.OS_VERSION) < 6.0) {
                    Roaming = new File(HOME, "Application Data");
                }
                else Roaming = new File(HOME, "AppData\\Roaming");
            }
            else Roaming = new File(APPDATA);
            BASE_CACHE_DIR = Local;
            BASE_FILES_DIR = BASE_CONFIG_DIR = Roaming;
        }
        else if (JavaSEPlatform.isMac) {
            BASE_CACHE_DIR = new File(HOME, "Library/Caches");
            BASE_CONFIG_DIR = new File(HOME, "Library/Preferences");
            BASE_FILES_DIR = new File(HOME, "Library/Application Support");
        }
        else if (JavaSEPlatform.isX11) {
            String XDG_CACHE_HOME = System.getenv("XDG_CACHE_HOME");
            String XDG_CONFIG_HOME = System.getenv("XDG_CONFIG_HOME");
            String XDG_DATA_HOME = System.getenv("XDG_DATA_HOME");
            if (XDG_CACHE_HOME == null) BASE_CACHE_DIR = new File(HOME, ".cache");
            else BASE_CACHE_DIR = new File(XDG_CACHE_HOME);
            if (XDG_CONFIG_HOME == null) BASE_CONFIG_DIR = new File(HOME, ".config");
            else BASE_CONFIG_DIR = new File(XDG_CONFIG_HOME);
            if (XDG_DATA_HOME == null) BASE_FILES_DIR = new File(HOME, ".local/share");
            else BASE_FILES_DIR = new File(XDG_DATA_HOME);
        }
        else {
            BASE_CACHE_DIR = new File(HOME, ".cache");
            BASE_CONFIG_DIR = new File(HOME, ".config");
            BASE_FILES_DIR = new File(HOME, ".local/share");
        }
    }
    public static final Producer PRODUCER = new Producer();
    public static final String ORGANIZATION_NAME = System.getProperty("io.notcute.app.javase.organizationname", "");
    public static final String APPLICATION_NAME = System.getProperty("io.notcute.app.javase.applicationname", "");

    protected static class Holder implements Context.Holder {
        private final JavaSEContext context;
        public Holder(JavaSEContext context) {
            this.context = Objects.requireNonNull(context);
            PRODUCER.putIfAbsent(new Identifier("notcute", "assets"), this::getAssets);
            PRODUCER.putIfAbsent(new Identifier("notcute", "logger"), this::getLogger);
            PRODUCER.putIfAbsent(new Identifier("notcute", "platform"), this::getPlatform);
        }
        @Override
        public Context getContext() {
            return context;
        }
        @Override
        public Producer getProducer() {
            return JavaSEContext.PRODUCER;
        }
        @Override
        public Dispatcher getDispatcher() {
            return Dispatcher.getDefaultDispatcher();
        }
        private static final Map<String, JavaSEPreferences> preferencesMap = new ConcurrentHashMap<>();
        private File getPreferencesFile(String name) {
            Objects.requireNonNull(name);
            return new File(getConfigDir(), name + ".xml");
        }
        @Override
        public Preferences getPreferences(String name) {
            Objects.requireNonNull(name);
            if (!preferencesMap.containsKey(name)) {
                preferencesMap.put(name, new JavaSEPreferences(getPreferencesFile(name)));
            }
            return preferencesMap.get(name);
        }
        @Override
        public boolean deletePreferences(String name) {
            Objects.requireNonNull(name);
            if (preferencesMap.containsKey(name)) {
                JavaSEPreferences preferences = preferencesMap.get(name);
                preferences.clear();
                return preferences.getFile().delete();
            }
            else return getPreferencesFile(name).delete();
        }
        @Override
        public File getConfigDir() {
            String ext;
            if (JavaSEPlatform.isWindows) ext = "Settings";
            else ext = "";
            File configDir = new File(BASE_CONFIG_DIR, ORGANIZATION_NAME + "/" + APPLICATION_NAME + "/" + ext);
            Util.createDirIfNotExist(configDir);
            return configDir;
        }
        @Override
        public File getCacheDir() {
            String ext;
            if (JavaSEPlatform.isWindows) ext = "Cache";
            else ext = "";
            File cacheDir = new File(BASE_CACHE_DIR, ORGANIZATION_NAME + "/" + APPLICATION_NAME + "/" + ext);
            Util.createDirIfNotExist(cacheDir);
            return cacheDir;
        }
        @Override
        public File getFilesDir(String type) {
            if (type == null) type = "";
            File filesDir = new File(new File(BASE_FILES_DIR, ORGANIZATION_NAME + "/" + APPLICATION_NAME), type);
            Util.createDirIfNotExist(filesDir);
            return filesDir;
        }
        @Override
        public File getHomeDir() {
            return HOME;
        }
        @Override
        public File getTmpDir() {
            return TMPDIR;
        }
        private static volatile JavaSEAssets assets = null;
        @Override
        public Assets getAssets() {
            if (assets == null) assets = new JavaSEAssets();
            return assets;
        }
        private static volatile JavaSELogger logger = null;
        @Override
        public Logger getLogger() {
            if (logger == null) logger = new JavaSELogger();
            return logger;
        }
        private static volatile JavaSEPlatform platform = null;
        @Override
        public Platform getPlatform() {
            if (platform == null) platform = new JavaSEPlatform();
            return platform;
        }
    }

    private volatile boolean disposed = false;
    private final Holder holder;
    public JavaSEContext() {
        holder = new Holder(this);
    }

    @Override
    public Context.Holder getContextHolder() {
        return holder;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
    }

}

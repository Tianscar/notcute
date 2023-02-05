package io.notcute.app.android;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.Preferences;
import io.notcute.context.Context;
import io.notcute.context.Producer;
import io.notcute.util.signalslot.Dispatcher;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AndroidContext implements Context {

    public static final File TMPDIR;
    static {
        String tmpDirPath = System.getProperty("java.io.tmpdir");
        if (tmpDirPath == null) TMPDIR = null;
        else TMPDIR = new File(tmpDirPath);
    }

    public static final Producer PRODUCER = new Producer();

    protected static class Holder implements Context.Holder {

        private final AndroidContext context;
        public Holder(AndroidContext context) {
            this.context = Objects.requireNonNull(context);
        }

        @Override
        public Context getContext() {
            return context;
        }

        @Override
        public Producer getProducer() {
            return PRODUCER;
        }

        @Override
        public Dispatcher getDispatcher() {
            return Dispatcher.getDefaultDispatcher();
        }

        private static final Map<String, AndroidPreferences> preferencesMap = new ConcurrentHashMap<>();
        @Override
        public Preferences getPreferences(String name) {
            Objects.requireNonNull(name);
            if (!preferencesMap.containsKey(name)) {
                preferencesMap.put(name, new AndroidPreferences(context.androidContext.getSharedPreferences(name, android.content.Context.MODE_PRIVATE), name));
            }
            return preferencesMap.get(name);
        }

        @Override
        public boolean deletePreferences(String name) {
            Objects.requireNonNull(name);
            return Util.deleteSharedPreferences(context.androidContext, name);
        }

        @Override
        public File getConfigDir() {
            return Util.getSharedPreferencesDir(context.androidContext);
        }

        @Override
        public File getCacheDir() {
            File cacheDir = Util.isExternalStorageWriteable() ? context.androidContext.getExternalCacheDir() : context.androidContext.getCacheDir();
            Util.createDirIfNotExist(cacheDir);
            return cacheDir;
        }

        @Override
        public File getFilesDir(String type) {
            if (type == null) type = "";
            final File filesDir = Util.isExternalStorageWriteable() ? context.androidContext.getExternalFilesDir(type) :
                    new File(context.androidContext.getFilesDir(), type);
            Util.createDirIfNotExist(filesDir);
            return filesDir;
        }

        @Override
        public File getHomeDir() {
            return Util.getStorageDir();
        }

        @Override
        public File getTmpDir() {
            return TMPDIR == null ? getFilesDir("tmp") : TMPDIR;
        }

        private static volatile AndroidAssets assets = null;
        @Override
        public Assets getAssets() {
            if (assets == null) assets = new AndroidAssets(context.androidContext.getAssets());
            return assets;
        }
        private static volatile AndroidLogger logger = null;
        @Override
        public Logger getLogger() {
            if (logger == null) logger = new AndroidLogger();
            return logger;
        }
        private static volatile AndroidPlatform platform = null;
        @Override
        public Platform getPlatform() {
            if (platform == null) platform = new AndroidPlatform();
            return platform;
        }

    }

    private final android.content.Context androidContext;
    private volatile boolean disposed = false;
    private final Holder holder;
    public AndroidContext(android.content.Context context) {
        androidContext = Objects.requireNonNull(context);
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

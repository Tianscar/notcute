package io.notcute.app.android;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.Preferences;
import io.notcute.context.Context;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;
import io.notcute.internal.android.AndroidUtils;
import io.notcute.util.FileUtils;
import io.notcute.util.I18NText;
import io.notcute.util.MIMETypes;
import io.notcute.util.signalslot.Dispatcher;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AndroidContext implements Context {

    static {
        Initializer.runInitializers();
    }

    public static final File TMPDIR;
    static {
        String tmpDirPath = System.getProperty("java.io.tmpdir");
        if (tmpDirPath == null) TMPDIR = null;
        else TMPDIR = new File(tmpDirPath);
    }

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
            return AndroidUtils.deleteSharedPreferences(context.androidContext, name);
        }

        @Override
        public File getConfigDir() {
            return AndroidUtils.getSharedPreferencesDir(context.androidContext);
        }

        @Override
        public File getCacheDir() {
            File cacheDir = AndroidUtils.isExternalStorageWriteable() ? context.androidContext.getExternalCacheDir() : context.androidContext.getCacheDir();
            FileUtils.createDirIfNotExist(cacheDir);
            return cacheDir;
        }

        @Override
        public File getFilesDir(String type) {
            if (type == null) type = "";
            final File filesDir = AndroidUtils.isExternalStorageWriteable() ? context.androidContext.getExternalFilesDir(type) :
                    new File(context.androidContext.getFilesDir(), type);
            FileUtils.createDirIfNotExist(filesDir);
            return filesDir;
        }

        @Override
        public File getHomeDir() {
            return AndroidUtils.getStorageDir();
        }

        @Override
        public File getTmpDir() {
            return TMPDIR == null ? getFilesDir("tmp") : TMPDIR;
        }

        @Override
        public Assets getAssets() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "assets"), Assets.class);
        }

        @Override
        public Logger getLogger() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "logger"), Logger.class);
        }

        @Override
        public Platform getPlatform() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "platform"), Platform.class);
        }

        @Override
        public I18NText getI18NText() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "i18NText"), I18NText.class);
        }

        @Override
        public MIMETypes getMIMETypes() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "mimeTypes"), MIMETypes.class);
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

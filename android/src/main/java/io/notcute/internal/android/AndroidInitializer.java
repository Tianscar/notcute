package io.notcute.internal.android;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.android.AndroidAssets;
import io.notcute.app.android.AndroidLogger;
import io.notcute.app.android.AndroidPlatform;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;

import static io.notcute.internal.android.AndroidShared.getContext;
import static io.notcute.internal.android.AndroidShared.hasContext;

public class AndroidInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "assets"), AndroidInitializer::getAssets);
        Producer.GLOBAL.put(new Identifier("notcute", "logger"), AndroidInitializer::getLogger);
        Producer.GLOBAL.put(new Identifier("notcute", "platform"), AndroidInitializer::getPlatform);
    }

    private static volatile AndroidAssets assets = null;
    public synchronized static Assets getAssets() {
        if (assets == null && hasContext()) assets = new AndroidAssets(getContext().getAssets());
        return assets;
    }
    private static volatile AndroidLogger logger = null;
    public synchronized static Logger getLogger() {
        if (logger == null) logger = new AndroidLogger();
        return logger;
    }
    private static volatile AndroidPlatform platform = null;
    public synchronized static Platform getPlatform() {
        if (platform == null) platform = new AndroidPlatform();
        return platform;
    }

}

package io.notcute.internal.javase;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.javase.JavaSEAssets;
import io.notcute.app.javase.JavaSELogger;
import io.notcute.app.javase.JavaSEPlatform;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;

public class JavaSEInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "assets"), JavaSEInitializer::getAssets);
        Producer.GLOBAL.put(new Identifier("notcute", "logger"), JavaSEInitializer::getLogger);
        Producer.GLOBAL.put(new Identifier("notcute", "platform"), JavaSEInitializer::getPlatform);
    }

    private static volatile JavaSEAssets assets = null;
    public static Assets getAssets() {
        if (assets == null) assets = new JavaSEAssets();
        return assets;
    }
    private static volatile JavaSELogger logger = null;
    public static Logger getLogger() {
        if (logger == null) logger = new JavaSELogger();
        return logger;
    }
    private static volatile JavaSEPlatform platform = null;
    public static Platform getPlatform() {
        if (platform == null) platform = new JavaSEPlatform();
        return platform;
    }

}

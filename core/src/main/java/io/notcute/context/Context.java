package io.notcute.context;

import io.notcute.app.Assets;
import io.notcute.app.Logger;
import io.notcute.app.Platform;
import io.notcute.app.Preferences;
import io.notcute.util.Disposable;
import io.notcute.util.signalslot.Dispatcher;

import java.io.File;

public interface Context extends Disposable {

    interface Holder {

        Context getContext();

        Producer getProducer();
        Dispatcher getDispatcher();

        Preferences getPreferences(String name);
        boolean deletePreferences(String name);

        File getConfigDir();
        File getCacheDir();
        File getFilesDir(String type);
        File getHomeDir();
        File getTmpDir();

        Assets getAssets();
        Logger getLogger();
        Platform getPlatform();

    }

    Holder getContextHolder();

}

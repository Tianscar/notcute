package io.notcute.app.android;

import android.content.res.AssetManager;
import io.notcute.app.Assets;
import io.notcute.util.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AndroidAssets implements Assets {

    private final AssetManager assets;

    public AndroidAssets(AssetManager assets) {
        this.assets = Objects.requireNonNull(assets);
    }

    public AssetManager getAssets() {
        return assets;
    }

    @Override
    public InputStream readAsset(String asset) {
        Objects.requireNonNull(asset);
        try {
            return assets.open(
                    FileUtils.removeStartSeparator(asset),
                    AssetManager.ACCESS_STREAMING);
        } catch (IOException ignored) {
        }
        return null;
    }

}

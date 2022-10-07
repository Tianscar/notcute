package com.ansdoship.a3wt.android;

import android.content.res.AssetManager;
import android.net.Uri;
import com.ansdoship.a3wt.app.A3Assets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.removeStartSeparator;

public class AndroidA3Assets implements A3Assets {

    protected final AssetManager assets;

    public AndroidA3Assets(final AssetManager assets) {
        checkArgNotNull(assets, "assets");
        this.assets = assets;
    }

    public AssetManager getAssets() {
        return assets;
    }

    @Override
    public InputStream readAsset(final String asset) {
        checkArgNotEmpty(asset, "asset");
        try {
            return assets.open(removeStartSeparator(asset), AssetManager.ACCESS_STREAMING);
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    public URL getAssetURL(final String asset) {
        checkArgNotNull(asset, "asset");
        try {
            return new URL("file:///android_asset/" + removeStartSeparator(asset));
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    public Uri getAssetUri(final String asset) {
        checkArgNotNull(asset, "asset");
        return Uri.parse("file:///android_asset/" + removeStartSeparator(asset));
    }

}

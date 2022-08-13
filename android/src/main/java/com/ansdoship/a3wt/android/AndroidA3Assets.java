package com.ansdoship.a3wt.android;

import android.content.res.AssetManager;
import android.net.Uri;
import com.ansdoship.a3wt.app.A3Assets;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3FileUtils.removeStartSeparator;

public class AndroidA3Assets implements A3Assets {

    protected final AssetManager assets;

    public AndroidA3Assets(AssetManager assets) {
        this.assets = assets;
    }

    public AssetManager getAssets() {
        return assets;
    }

    @Override
    public InputStream readAsset(String asset) {
        try {
            return assets.open(removeStartSeparator(asset), AssetManager.ACCESS_STREAMING);
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    public URL getAssetURL(String asset) {
        try {
            return new URL("file:///android_asset/" + removeStartSeparator(asset));
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    public Uri getAssetUri(String asset) {
        return Uri.parse("file:///android_asset/" + removeStartSeparator(asset));
    }

}

package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3FileUtils.ensureStartSeparatorUNIX;
import static com.ansdoship.a3wt.util.Asserts.notSupportedYetOnPlatform;

public class AWTA3Assets implements A3Assets {

    @Override
    public InputStream readAsset(String asset) {
        return AWTA3Assets.class.getClassLoader().getResourceAsStream(ensureStartSeparatorUNIX(asset));
    }

    @Override
    public URL getAssetURL(String asset) {
        return AWTA3Assets.class.getClassLoader().getResource(ensureStartSeparatorUNIX(asset));
    }

    @Override
    public String[] listAssets(String asset) throws IOException {
        notSupportedYetOnPlatform("A3Assets.listAssets(String)");
        return null;
    }

}

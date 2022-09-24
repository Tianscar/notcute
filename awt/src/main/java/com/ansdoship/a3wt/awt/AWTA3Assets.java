package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.InputStream;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.ensureStartSeparatorUNIX;

public class AWTA3Assets implements A3Assets {

    @Override
    public InputStream readAsset(final String asset) {
        checkArgNotEmpty(asset, "asset");
        return AWTA3Assets.class.getClassLoader().getResourceAsStream(ensureStartSeparatorUNIX(asset));
    }

    @Override
    public URL getAssetURL(final String asset) {
        checkArgNotNull(asset, "asset");
        return AWTA3Assets.class.getClassLoader().getResource(ensureStartSeparatorUNIX(asset));
    }

}

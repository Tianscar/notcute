package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.InputStream;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.removeStartSeparator;

public class AWTA3Assets implements A3Assets {

    @Override
    public InputStream readAsset(final String asset) {
        checkArgNotEmpty(asset, "asset");
        return AWTA3Assets.class.getClassLoader().getResourceAsStream(removeStartSeparator(asset));
    }

    @Override
    public URL getAssetURL(final String asset) {
        checkArgNotNull(asset, "asset");
        return AWTA3Assets.class.getClassLoader().getResource(removeStartSeparator(asset));
    }

}

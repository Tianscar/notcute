package com.ansdoship.a3wt.app;

import java.io.InputStream;
import java.net.URL;

public interface A3Assets {

    InputStream readAsset(final String asset);
    URL getAssetURL(final String asset);

}

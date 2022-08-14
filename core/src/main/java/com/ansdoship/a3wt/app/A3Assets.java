package com.ansdoship.a3wt.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface A3Assets {

    InputStream readAsset(String asset);
    URL getAssetURL(String asset);
    String[] listAssets(String asset) throws IOException;

}

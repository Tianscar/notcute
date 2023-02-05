package io.notcute.app.javase;

import io.notcute.app.Assets;

import java.io.InputStream;

public class JavaSEAssets implements Assets {

    @Override
    public InputStream readAsset(final String asset) {
        return JavaSEAssets.class.getClassLoader().getResourceAsStream(
                (asset.charAt(0) == '\\' || asset.charAt(0) == '/') ?
                        asset.substring(1) : asset);
    }

}

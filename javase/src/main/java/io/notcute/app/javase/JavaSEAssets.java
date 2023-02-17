package io.notcute.app.javase;

import io.notcute.app.Assets;
import io.notcute.util.FileUtils;

import java.io.InputStream;

public class JavaSEAssets implements Assets {

    @Override
    public InputStream readAsset(final String asset) {
        return JavaSEAssets.class.getClassLoader().getResourceAsStream(FileUtils.removeStartSeparator(asset));
    }

}

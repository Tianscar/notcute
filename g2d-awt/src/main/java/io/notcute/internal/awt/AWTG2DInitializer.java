package io.notcute.internal.awt;

import io.notcute.context.Initializer;

import javax.imageio.ImageIO;

public class AWTG2DInitializer extends Initializer {

    @Override
    public void initialize() {
        ImageIO.setUseCache(false);
    }

}

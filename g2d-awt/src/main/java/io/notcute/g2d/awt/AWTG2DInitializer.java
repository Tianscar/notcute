package io.notcute.g2d.awt;

import io.notcute.context.Initializer;

import javax.imageio.ImageIO;

public class AWTG2DInitializer implements Initializer {

    @Override
    public void initialize() {
        ImageIO.setUseCache(false);
    }

}

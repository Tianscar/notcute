package io.notcute.internal.awt;

import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;
import io.notcute.g2d.awt.AWTGraphicsKit;

import javax.imageio.ImageIO;

public class AWTG2DInitializer extends Initializer {

    @Override
    public void initialize() {
        ImageIO.setUseCache(false);
        Producer.GLOBAL.put(new Identifier("notcute", "graphicsKit"), AWTG2DInitializer::getGraphicsKit);
    }

    private static volatile AWTGraphicsKit graphicsKit = null;
    public synchronized static AWTGraphicsKit getGraphicsKit() {
        if (graphicsKit == null) {
            graphicsKit = new AWTGraphicsKit();
            graphicsKit.getExpansions().add(new BMPAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new JPEGAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new PNGAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new GIFAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new TIFFAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new WBMPAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new ICNSAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new ICOAWTGraphicsKitExpansion());
            graphicsKit.getExpansions().add(new TGAAWTGraphicsKitExpansion());
        }
        return graphicsKit;
    }

}

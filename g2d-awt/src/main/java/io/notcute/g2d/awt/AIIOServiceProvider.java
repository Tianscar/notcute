package io.notcute.g2d.awt;

import io.notcute.g2d.AnimatedImage;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;

public interface AIIOServiceProvider {

    AnimatedImage read(ImageInputStream stream) throws IOException;
    boolean write(AnimatedImage im, String formatName, float quality, ImageOutputStream output) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

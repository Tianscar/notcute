package io.notcute.g2d.awt;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface BIIOServiceProvider {

    BufferedImage read(ImageInputStream stream) throws IOException;
    boolean write(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException;

    String[] getReaderMIMETypes();
    String[] getWriterMIMETypes();

}

package io.notcute.g2d.awt;

import io.notcute.g2d.MultiFrameImage;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;

public interface MFBIIOServiceProvider {

    MultiFrameImage read(ImageInputStream stream) throws IOException;
    boolean write(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException;

    String[] getReaderMIMETypes();
    String[] getWriterMIMETypes();

}

package a3wt.awt;

import a3wt.graphics.A3FramedImage;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;

public interface FIIOServiceProvider {

    A3FramedImage read(ImageInputStream stream) throws IOException;
    boolean write(A3FramedImage im, String formatName, float quality, ImageOutputStream output) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

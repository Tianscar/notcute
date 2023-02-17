package io.notcute.g2d;

import io.notcute.app.Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface GraphicsKit {

    Image createImage(int width, int height, int type);

    Image readImage(File input, int type);
    Image readImage(InputStream input, int type);
    Image readImage(URL input, int type);
    Image readImage(Assets assets, String input, int type);
    MultiFrameImage readMultiFrameImage(File input, int type);
    MultiFrameImage readMultiFrameImage(InputStream input, int type);
    MultiFrameImage readMultiFrameImage(URL input, int type);
    MultiFrameImage readMultiFrameImage(Assets assets, String input, int type);

    boolean writeImage(Image image, String mimeType, int quality, File output);
    boolean writeImage(Image image, String mimeType, int quality, OutputStream output);
    boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, File output);
    boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, OutputStream output);

    String[] getImageReaderMIMETypes();
    String[] getImageWriterMIMETypes();
    String[] getMultiFrameImageReaderMIMETypes();
    String[] getMultiFrameImageWriterMIMETypes();

    Font getFont(String familyName, int style);
    Font getDefaultFont();

    Font readFont(File input);
    Font readFont(Assets assets, String input);

}

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
    AnimatedImage readAnimatedImage(File input, int type);
    AnimatedImage readAnimatedImage(InputStream input, int type);
    AnimatedImage readAnimatedImage(URL input, int type);
    AnimatedImage readAnimatedImage(Assets assets, String input, int type);

    boolean writeImage(Image image, String formatName, int quality, File output);
    boolean writeImage(Image image, String formatName, int quality, OutputStream output);
    boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, File output);
    boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, OutputStream output);

    String[] getImageReaderFormatNames();
    String[] getImageWriterFormatNames();
    String[] getAnimatedImageReaderFormatNames();
    String[] getAnimatedImageWriterFormatNames();

    Font getFont(String familyName, int style);
    Font getDefaultFont();

    Font readFont(File input);
    Font readFont(Assets assets, String input);

}

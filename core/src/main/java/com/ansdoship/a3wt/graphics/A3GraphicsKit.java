package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

public interface A3GraphicsKit {

    A3Image createImage(int width, int height);
    default A3FramedImage createFramedImage(A3Image... frames) {
        return new DefaultA3FramedImage(frames);
    }
    default A3FramedImage createFramedImage(Collection<A3Image> frames) {
        return new DefaultA3FramedImage(frames);
    }
    default A3FramedImage createFramedImage(Iterator<A3Image> frames) {
        return new DefaultA3FramedImage(frames);
    }

    A3Image readImage(File input);
    A3Image readImage(InputStream input);
    A3Image readImage(URL input);
    A3Image readImage(A3Assets assets, String input);
    A3FramedImage readFramedImage(File input);
    A3FramedImage readFramedImage(InputStream input);
    A3FramedImage readFramedImage(URL input);
    A3FramedImage readFramedImage(A3Assets assets, String input);

    boolean writeImage(A3Image image, String formatName, int quality, File output);
    boolean writeImage(A3Image image, String formatName, int quality, OutputStream output);
    boolean writeFramedImage(A3FramedImage image, String formatName, int quality, File output);
    boolean writeFramedImage(A3FramedImage image, String formatName, int quality, OutputStream output);

    String[] getImageReaderFormatNames();
    String[] getImageWriterFormatNames();

    default A3Graphics.Data createGraphicsData() {
        return new A3Graphics.DefaultData();
    }

    A3Path createPath();

    A3Font readFont(File input);
    A3Font readFont(A3Assets assets, String input);
    A3Font readFont(String familyName, int style);

    A3Font getDefaultFont();

    A3Cursor createCursor(int type);
    A3Cursor createCursor(A3Image image);

}

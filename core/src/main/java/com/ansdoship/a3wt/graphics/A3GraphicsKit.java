package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface A3GraphicsKit {

    A3Image createImage(int width, int height);

    A3Image readImage(File input);
    A3Image readImage(InputStream input);
    A3Image readImage(URL input);
    A3Image readImage(A3Assets assets, String input);
    boolean writeImage(A3Image image, String formatName, File output);
    boolean writeImage(A3Image image, String formatName, OutputStream output);
    String[] getImageReaderFormatNames();
    String[] getImageWriterFormatNames();

    A3Path createPath();

    A3Font readFont(File input);
    A3Font readFont(A3Assets assets, String input);
    A3Font readFont(String familyName, int style);

}

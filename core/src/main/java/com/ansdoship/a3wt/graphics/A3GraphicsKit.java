package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface A3GraphicsKit {

    A3Image readImage(File input) throws IOException;
    A3Image readImage(InputStream input) throws IOException;
    A3Image readImage(URL input) throws IOException;
    A3Image readImage(A3Assets assets, String input) throws IOException;
    boolean writeImage(A3Image image, String formatName, File output) throws IOException;
    boolean writeImage(A3Image image, String formatName, OutputStream output) throws IOException;
    String[] getImageReaderFormatNames();
    String[] getImageWriterFormatNames();

    A3Path createPath();

    A3Font readFont(File input) throws IOException;
    A3Font readFont(InputStream input) throws IOException;
    A3Font readFont(URL input) throws IOException;
    A3Font readFont(A3Assets assets, String input) throws IOException;
    A3Font readFont(String familyName, int style);

}

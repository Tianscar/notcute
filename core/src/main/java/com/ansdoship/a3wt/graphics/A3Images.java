package com.ansdoship.a3wt.graphics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface A3Images {

    A3Image read(File input) throws IOException;
    A3Image read(InputStream input) throws IOException;
    A3Image read(URL input) throws IOException;
    boolean write(A3Image image, String formatName, File output) throws IOException;
    boolean write(A3Image image, String formatName, OutputStream output) throws IOException;
    String[] getReaderFormatNames();
    String[] getWriterFormatNames();
    A3Image copy(A3Image source);

}

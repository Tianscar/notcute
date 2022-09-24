package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FIIOServiceProvider {

    A3FramedImage read(InputStream stream) throws IOException;
    boolean canRead(InputStream stream) throws IOException;
    boolean write(A3FramedImage im, String formatName, float quality, OutputStream output) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

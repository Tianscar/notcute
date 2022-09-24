package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.ansdoship.a3wt.util.A3Arrays.copy;

public final class TiffFIIOSpi implements FIIOServiceProvider {

    private static final String[] READER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};

    @Override
    public A3FramedImage read(final InputStream stream) throws IOException {
        return null;
    }

    @Override
    public boolean canRead(InputStream stream) throws IOException {
        return false;
    }

    @Override
    public boolean write(final A3FramedImage im, final String formatName, final float quality, final OutputStream output) throws IOException {
        return false;
    }

    @Override
    public String[] getReaderFormatNames() {
        return copy(READER_FORMAT_NAMES);
    }

    @Override
    public String[] getWriterFormatNames() {
        return copy(WRITER_FORMAT_NAMES);
    }

}

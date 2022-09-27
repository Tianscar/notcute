package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.Predicate;

public interface FIIOServiceProvider {

    A3FramedImage read(InputStream stream) throws IOException;
    boolean canRead(InputStream stream) throws IOException;
    default boolean canWrite(final String formatName) {
        return Arrays.stream(getWriterFormatNames()).anyMatch(new Predicate<String>() {
            @Override
            public boolean test(final String s) {
                return s.equalsIgnoreCase(formatName);
            }
        });
    }
    boolean write(A3FramedImage im, String formatName, float quality, OutputStream output) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

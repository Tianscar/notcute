package io.notcute.g2d.swt;

import io.notcute.g2d.Image;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SIIOServiceProvider {

    Image read(Device device, InputStream stream) throws IOException, SWTException;
    boolean write(Image im, String formatName, int quality, OutputStream output) throws IOException, SWTException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();
    String[] getAnimatedImageReaderFormatNames();
    String[] getAnimatedImageWriterFormatNames();

}

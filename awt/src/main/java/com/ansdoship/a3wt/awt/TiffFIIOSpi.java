package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.DefaultA3FramedImage;
import com.twelvemonkeys.imageio.metadata.tiff.TIFF;

import javax.imageio.IIOImage;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import static com.ansdoship.a3wt.util.A3Arrays.copy;
import static com.ansdoship.a3wt.util.A3Files.readNBytes;

public final class TiffFIIOSpi implements FIIOServiceProvider {

    private static final String[] READER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};

    @Override
    public A3FramedImage read(final InputStream stream) throws IOException {
        if (!stream.markSupported()) throw new IOException("stream should support mark!");
        ImageReader reader;
        if ((reader = getTiffImageReader("tiff")) == null) {
            reader = getTiffImageReader("bigtiff");
        }
        if (reader == null) return null;
        final AWTA3Image[] images = new AWTA3Image[reader.getNumImages(true)];
        for (int i = 0; i < images.length; i ++) {
            images[i] = new AWTA3Image(reader.read(i), 0, 0, 0);
        }
        try {
            stream.close();
        }
        catch (final IOException ignored) {
        }
        reader.dispose();
        return new DefaultA3FramedImage(images);
    }

    @Override
    public boolean canRead(final InputStream stream) throws IOException {
        if (!stream.markSupported()) throw new IOException("stream should support mark!");
        stream.mark(Integer.MAX_VALUE);
        try {
            return isTiff(stream, TIFF.TIFF_MAGIC) || isTiff(stream, TIFF.BIGTIFF_MAGIC);
        }
        finally {
            stream.reset();
        }
    }

    private static boolean isTiff(final InputStream stream, final int versionMagic) throws IOException {
        try {
            final byte[] magic = new byte[4];
            readNBytes(stream, magic, 0, magic.length);

            return magic[0] == 'I' && magic[1] == 'I' && magic[2] == (versionMagic & 0xFF) && magic[3] == (versionMagic >>> 8)
                    || magic[0] == 'M' && magic[1] == 'M' && magic[2] == (versionMagic >>> 8) && magic[3] == (versionMagic & 0xFF);
        }
        catch (final EOFException e) {
            return false;
        }
    }

    @Override
    public boolean write(final A3FramedImage im, final String formatName, final float quality, final OutputStream output) throws IOException {
        final ImageWriter writer = getTiffImageWriter(formatName);
        if (writer == null) return false;
        try (final ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(ios);
            final ImageWriteParam params = writer.getDefaultWriteParam();
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            params.setCompressionType("LZW");
            params.setCompressionQuality(quality);
            writer.prepareWriteSequence(null);
            for (final A3Image i : im) {
                writer.writeToSequence(new IIOImage(((AWTA3Image)i).bufferedImage, null, null), params);
            }
            writer.endWriteSequence();
        }
        writer.dispose();
        return true;
    }

    private static ImageReader getTiffImageReader(final String formatName) {
        final Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(formatName);
        if (it.hasNext()) return it.next();
        return null;
    }

    private static ImageWriter getTiffImageWriter(final String formatName) {
        final Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(formatName);
        if (it.hasNext()) return it.next();
        return null;
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

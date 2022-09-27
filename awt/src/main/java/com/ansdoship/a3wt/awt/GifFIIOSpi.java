package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.DefaultA3FramedImage;
import com.ansdoship.a3wt.util.A3Math;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.ansdoship.a3wt.awt.A3AWTUtils.getAlignedImage;
import static com.ansdoship.a3wt.util.A3Arrays.copy;
import static com.madgag.gif.fmsware.GifDecoder.STATUS_OK;

public final class GifFIIOSpi implements FIIOServiceProvider {

    //private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    private static final String[] READER_FORMAT_NAMES = new String[]{"gif"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"gif"};

    @Override
    public A3FramedImage read(final InputStream stream) throws IOException {
        if (!stream.markSupported()) throw new IOException("stream should support mark!");
        final GifDecoder decoder = new GifDecoder();
        final int status = decoder.read(stream);
        if (status == STATUS_OK) {
            final AWTA3Image[] images = new AWTA3Image[decoder.getFrameCount()];
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                images[i] = new AWTA3Image(decoder.getFrame(i), decoder.getDelay(i), 0, 0);
            }
            return new DefaultA3FramedImage(images);
        }
        return null;
    }

    @Override
    public boolean canRead(final InputStream stream) throws IOException {
        if (!stream.markSupported()) throw new IOException("stream should support mark!");
        stream.mark(Integer.MAX_VALUE);
        try {
            return isGif(stream);
        }
        finally {
            stream.reset();
        }
    }

    @Override
    public boolean write(final A3FramedImage im, final String formatName, final float quality, final OutputStream output) throws IOException {
        final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(output);
        encoder.setRepeat(im.getLooping() + 1);
        encoder.setQuality((int) (A3Math.clamp((1 - quality) * 19, 1, 19) + 1));
        encoder.setBackground(Color.BLACK);
        encoder.setTransparent(null);
        //encoder.setBackground(TRANSPARENT);
        //encoder.setTransparent(TRANSPARENT, true);
        final int width = im.getGeneralWidth();
        final int height = im.getGeneralHeight();
        encoder.setSize(width, height);
        AWTA3Image image;
        for (final A3Image i : im) {
            image = (AWTA3Image) i;
            encoder.setDelay((int) A3Math.clamp(image.time, Integer.MIN_VALUE, Integer.MAX_VALUE));
            if (!encoder.addFrame(getAlignedImage(image.bufferedImage, 0, 0, width, height))) return false;
        }
        encoder.finish();
        return true;
    }

    @Override
    public String[] getReaderFormatNames() {
        return copy(READER_FORMAT_NAMES);
    }

    @Override
    public String[] getWriterFormatNames() {
        return copy(WRITER_FORMAT_NAMES);
    }

    private static boolean isGif(final InputStream stream) throws IOException {
        final StringBuilder id = new StringBuilder();
        for (int i = 0; i < 6; i ++) {
            id.append((char) stream.read());
        }
        return id.toString().startsWith("GIF");
    }

}

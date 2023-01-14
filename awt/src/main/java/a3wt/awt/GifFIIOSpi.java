package a3wt.awt;

import a3wt.graphics.A3FramedImage;
import a3wt.graphics.DefaultA3FramedImage;
import a3wt.util.A3Math;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static a3wt.awt.A3AWTUtils.getAlignedImage;
import static a3wt.awt.A3AWTUtils.getImage;
import static a3wt.util.A3Arrays.containsIgnoreCase;
import static a3wt.util.A3Arrays.copy;
import static com.madgag.gif.fmsware.GifDecoder.STATUS_OK;

public final class GifFIIOSpi implements FIIOServiceProvider {

    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    private static final String[] READER_FORMAT_NAMES = new String[]{"gif"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"gif"};

    @Override
    public A3FramedImage read(final ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            if (!isGif(stream)) return null;
        }
        finally {
            stream.reset();
        }
        final GifDecoder decoder = new GifDecoder();
        final int status = decoder.read(stream);
        if (status == STATUS_OK) {
            final A3FramedImage.Frame[] frames = new A3FramedImage.Frame[decoder.getFrameCount()];
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                frames[i] = new A3FramedImage.DefaultFrame(new AWTA3Image(decoder.getFrame(i)), decoder.getDelay(i));
            }
            final A3FramedImage result = new DefaultA3FramedImage(frames);
            result.setLooping(decoder.getLoopCount());
            return result;
        }
        return null;
    }

    @Override
    public boolean write(final A3FramedImage im, final String formatName, final float quality, final ImageOutputStream output) throws IOException {
        if (!containsIgnoreCase(WRITER_FORMAT_NAMES, formatName)) return false;
        final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(output);
        encoder.setRepeat(im.getLooping());
        encoder.setQuality((int) (A3Math.clamp((1 - quality) * 19, 1, 19) + 1));
        final int width = im.getGeneralWidth();
        final int height = im.getGeneralHeight();
        encoder.setSize(width, height);
        AWTA3Image image;
        for (final A3FramedImage.Frame frame : im) {
            image = (AWTA3Image) frame.getImage();
            if (image.hasAlpha()) {
                encoder.setBackground(TRANSPARENT);
                encoder.setTransparent(TRANSPARENT, true);
            }
            else {
                encoder.setBackground(Color.BLACK);
                encoder.setTransparent(null);
            }
            encoder.setDelay((int) A3Math.clamp(image.duration, Integer.MIN_VALUE, Integer.MAX_VALUE));
            if (!encoder.addFrame(getAlignedImage(getImage(image.bufferedImage, BufferedImage.TYPE_3BYTE_BGR),
                    0, 0, width, height))) return false;
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

    private static boolean isGif(final ImageInputStream stream) throws IOException {
        final StringBuilder id = new StringBuilder();
        for (int i = 0; i < 6; i ++) {
            id.append((char) stream.read());
        }
        return id.toString().startsWith("GIF");
    }

}

package a3wt.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import a3wt.graphics.A3FramedImage;
import a3wt.graphics.A3Image;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import pl.droidsonroids.gif.GifDrawable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;

import static a3wt.util.A3Arrays.containsIgnoreCase;
import static a3wt.util.A3Arrays.copy;
import static a3wt.util.A3Math.clamp;
import static a3wt.util.A3StreamUtils.MAX_BUFFER_SIZE;

public class GifFBIOSpi implements FBIOServiceProvider {

    private static final String[] READER_FORMAT_NAMES = new String[]{"gif"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"gif"};

    @Override
    public A3FramedImage read(InputStream stream, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) throw new IllegalArgumentException("stream should support mark!");
        A3FramedImage image;
        stream.mark(MAX_BUFFER_SIZE);
        try {
            image = mRead(stream, config);
        }
        finally {
            stream.reset();
        }
        if (image != null) stream.close();
        return image;
    }

    private A3FramedImage mRead(InputStream stream, Bitmap.Config config) {
        GifDrawable drawable;
        try {
            drawable = new GifDrawable(stream);
        }
        catch (IOException e) {
            drawable = null;
        }
        if (drawable == null) return null;
        else {
            return A3AndroidUtils.gifDrawable2FramedImage(drawable, config);
        }
    }

    @Override
    public A3FramedImage read(File file, Bitmap.Config config) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(stream.getFD()), config);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        try {
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(descriptor.getFileDescriptor()), config);
        }
        catch (IOException e) {
            return null;
        }
    }


    @Override
    public A3FramedImage read(byte[] data, int offset, int length, Bitmap.Config config) {
        try {
            byte[] bytes = new byte[length];
            System.arraycopy(data, offset, bytes, 0, length);
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(bytes), config);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        try {
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(assets, asset), config);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage read(Resources res, int id, Bitmap.Config config) throws IOException {
        try {
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(res, id), config);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        try {
            return A3AndroidUtils.gifDrawable2FramedImage(new GifDrawable(resolver, uri), config);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage read(URI uri, Bitmap.Config config) throws IOException {
        return read(uri.toURL(), config);
    }

    @Override
    public A3FramedImage read(URL url, Bitmap.Config config) throws IOException {
        try (InputStream stream = url.openStream()) {
            return mRead(stream, config);
        }
    }

    @Override
    public boolean write(File output, A3FramedImage im, String formatName, int quality) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(output)) {
            return write(stream, im, formatName, quality);
        }
    }

    @Override
    public boolean write(OutputStream output, A3FramedImage im, String formatName, int quality) throws IOException {
        if (!containsIgnoreCase(WRITER_FORMAT_NAMES, formatName)) return false;
        final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(output);
        encoder.setRepeat(im.getLooping());
        encoder.setQuality((int) (clamp((1 - quality / 100f) * 19, 1, 19) + 1));
        //encoder.setTransparent(-1);
        final int width = im.getGeneralWidth();
        final int height = im.getGeneralHeight();
        encoder.setSize(width, height);
        AndroidA3Image image;
        for (final A3Image i : im) {
            image = (AndroidA3Image) i;
            encoder.setDelay((int) clamp(image.duration, Integer.MIN_VALUE, Integer.MAX_VALUE));
            if (!encoder.addFrame(A3AndroidUtils.getAlignedBitmap(A3AndroidUtils.getBitmap(image.bitmap, Bitmap.Config.RGB_565),
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

}

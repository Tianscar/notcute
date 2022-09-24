package com.ansdoship.a3wt.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.BitmapRegionDecoder;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3Arrays.copy;
import static com.ansdoship.a3wt.util.A3Math.clamp;

public final class BasicBIOSpi implements BIOServiceProvider {

    private static final String[] READER_FORMAT_NAMES = new String[]{"bmp", "webp", "png", "jpeg", "jpg"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"bmp", "webp", "png", "jpeg", "jpg"};

    @Override
    public Bitmap read(InputStream stream, Bitmap.Config config) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        stream.close();
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(InputStream stream, Rect region, Bitmap.Config config) throws IOException {
        BitmapRegionDecoder decoder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            decoder = BitmapRegionDecoder.newInstance(stream);
        }
        else {
            decoder = BitmapRegionDecoder.newInstance(stream, false);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = config;
        Bitmap bitmap = decoder.decodeRegion(region, options);
        stream.close();
        if (decoder != null) {
            if (!decoder.isRecycled()) {
                decoder.recycle();
            }
        }
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(File file, Bitmap.Config config) throws IOException {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        FileInputStream stream = new FileInputStream(file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(stream.getFD(), null, options);
        stream.close();
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(File file, Rect region, Bitmap.Config config) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        BitmapRegionDecoder decoder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            decoder = BitmapRegionDecoder.newInstance(stream);
        }
        else {
            decoder = BitmapRegionDecoder.newInstance(stream, false);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = config;
        Bitmap bitmap = decoder.decodeRegion(region, options);
        stream.close();
        if (decoder != null) {
            if (!decoder.isRecycled()) {
                decoder.recycle();
            }
        }
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(byte[] data, int offset, int length, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, options);
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(byte[] data, int offset, int length, Rect region, Bitmap.Config config) {
        Bitmap bitmap = null;
        BitmapRegionDecoder decoder = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                decoder = BitmapRegionDecoder.newInstance(data, offset, length);
            }
            else {
                decoder = BitmapRegionDecoder.newInstance(data, offset, length, false);
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inPreferredConfig = config;
            bitmap = decoder.decodeRegion(region, options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (decoder != null) {
                if (!decoder.isRecycled()) {
                    decoder.recycle();
                }
            }
        }
        if (bitmap != null) {
            if (!bitmap.hasAlpha()) {
                bitmap.setHasAlpha(true);
            }
        }
        return bitmap;
    }

    @Override
    public Bitmap read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        return read(assets.open(asset), config);
    }

    @Override
    public Bitmap read(AssetManager assets, String asset, Rect region, Bitmap.Config config) throws IOException {
        return read(assets.open(asset), region, config);
    }

    @Override
    public Bitmap read(Resources res, int id, Bitmap.Config config) throws IOException {
        Bitmap bitmap;
        try {
            bitmap = read(res.openRawResource(id), config);
        }
        catch (Resources.NotFoundException e) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inPreferredConfig = config;
            bitmap = BitmapFactory.decodeResource(res, id, options);
        }
        return bitmap;
    }

    @Override
    public Bitmap read(Resources res, int id, Rect region, Bitmap.Config config) throws IOException {
        return read(res.openRawResource(id), region, config);
    }

    @Override
    public Bitmap read(Drawable drawable, Bitmap.Config config) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            if (bitmap != null) {
                if (!bitmap.hasAlpha()) {
                    bitmap.setHasAlpha(true);
                }
            }
            return bitmap;
        }
        else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                return null;
            }
            else {
                Bitmap bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        config == null ? (
                                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565) :
                                config);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            }
        }
    }

    @Override
    public Bitmap read(Drawable drawable, Rect region, Bitmap.Config config) {
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            return null;
        }
        else {
            Bitmap bitmap = Bitmap.createBitmap(
                    region.width(),
                    region.height(),
                    config == null ? (
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565) :
                            config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(region.left, region.top, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    @Override
    public Bitmap read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        return read(resolver.openInputStream(uri), config);
    }

    @Override
    public Bitmap read(ContentResolver resolver, Uri uri, Rect region, Bitmap.Config config) throws IOException {
        return read(resolver.openInputStream(uri), region, config);
    }

    @Override
    public Bitmap read(URI uri, Bitmap.Config config) throws IOException {
        return read(uri.toURL(), config);
    }

    @Override
    public Bitmap read(URI uri, Rect region, Bitmap.Config config) throws IOException {
        return read(uri.toURL(), region, config);
    }

    @Override
    public Bitmap read(URL url, Bitmap.Config config) throws IOException {
        return read(url.openStream(), config);
    }

    @Override
    public Bitmap read(URL url, Rect region, Bitmap.Config config) throws IOException {
        return read(url.openStream(), region, config);
    }

    @Override
    public boolean write(File output, Bitmap bitmap, String formatName, int quality) throws IOException {
        if (output.exists()) {
            if (!output.delete()) return false;
        }
        if (!output.createNewFile()) return false;
        try (FileOutputStream fos = new FileOutputStream(output)) {
            return write(fos, bitmap, formatName, quality);
        }
    }

    @Override
    public boolean write(OutputStream output, Bitmap bitmap, String formatName, int quality) throws IOException {
        quality = clamp(quality, 0, 100);
        boolean result = false;
        String format = formatName.trim().toLowerCase();
        if (format.equals("png")) {
            result = bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        }
        else if (format.equals("jpg") || format.equals("jpeg")) {
            result = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output);
        }
        else if (format.equals("webp")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (quality == 100) {
                    result = bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS,
                            quality, output);
                }
                else {
                    result = bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY,
                            quality, output);
                }
            }
            else {
                result = bitmap.compress(Bitmap.CompressFormat.WEBP, quality, output);
            }
        }
        else if (format.equals("bmp")) {
            result = BitmapBMPEncoder.compress(bitmap, output);
        }
        output.flush();
        //output.close();
        return result;
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

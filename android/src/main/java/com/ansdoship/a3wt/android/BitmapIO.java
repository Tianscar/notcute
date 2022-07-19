/*
 * MIT License
 *
 * Copyright (c) 2021 Tianscar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.ansdoship.a3wt.android;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.*;
import java.net.URI;
import java.net.URL;

import static com.ansdoship.a3wt.util.A3MathUtils.clamp;

/**
 * A factory class that providing functions to decode and encode Android Bitmap.
 */
public final class BitmapIO {

    private BitmapIO(){}

    public static Bitmap read(@NonNull InputStream stream, @Nullable Bitmap.Config config) throws IOException {
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

    public static Bitmap read(@NonNull InputStream stream) throws IOException {
        return read(stream, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull InputStream stream, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
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

    public static Bitmap read(@NonNull InputStream stream, @NonNull Rect region) throws IOException {
        return read(stream, region, null);
    }

    public static Bitmap read(@NonNull File file, @Nullable Bitmap.Config config) throws IOException {
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

    public static Bitmap read(@NonNull File file) throws IOException {
        return read(file, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull File file, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
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

    public static Bitmap read(@NonNull File file, @NonNull Rect region) throws IOException {
        return read(file, region, null);
    }

    public static Bitmap read(@NonNull String pathname, @Nullable Bitmap.Config config) throws IOException {
        return read(new File(pathname), config);
    }

    public static Bitmap read(@NonNull String pathname) throws IOException {
        return read(pathname, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull String pathname, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(new File(pathname), region, config);
    }

    public static Bitmap read(@NonNull String pathname, @NonNull Rect region) throws IOException {
        return read(pathname, region, null);
    }

    public static Bitmap read(@NonNull byte[] data, @Nullable Bitmap.Config config) {
        return read(data, 0, data.length, config);
    }

    public static Bitmap read(@NonNull byte[] data) {
        return read(data, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull byte[] data, int offset, int length, @Nullable Bitmap.Config config) {
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

    public static Bitmap read(@NonNull byte[] data, int offset, int length) {
        return read(data, offset, length, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull byte[] data, @NonNull Rect region, @Nullable Bitmap.Config config) {
        return read(data, 0, data.length, region, config);
    }

    public static Bitmap read(@NonNull byte[] data, @NonNull Rect region) {
        return read(data, region, null);
    }

    public static Bitmap read(@NonNull byte[] data, int offset, int length, @NonNull Rect region, @Nullable Bitmap.Config config) {
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

    public static Bitmap read(@NonNull byte[] data, int offset, int length, @NonNull Rect region) {
        return read(data, offset, length, region, null);
    }

    public static Bitmap read(@NonNull AssetManager assets, @NonNull String asset, @Nullable Bitmap.Config config) throws IOException {
        return read(assets.open(asset), config);
    }

    public static Bitmap read(@NonNull AssetManager assets, @NonNull String asset) throws IOException {
        return read(assets, asset, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull AssetManager assets, @NonNull String asset, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(assets.open(asset), region, config);
    }

    public static Bitmap read(@NonNull AssetManager assets, @NonNull String asset, @NonNull Rect region) throws IOException {
        return read(assets, asset, region, null);
    }

    @SuppressLint("ResourceType")
    public static Bitmap read(@NonNull Resources res, int id, @Nullable Bitmap.Config config) throws IOException {
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

    public static Bitmap read(@NonNull Resources res, int id) throws IOException {
        return read(res, id, (Bitmap.Config) null);
    }

    @SuppressLint("ResourceType")
    public static Bitmap read(@NonNull Resources res, int id, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(res.openRawResource(id), region, config);
    }

    public static Bitmap read(@NonNull Resources res, int id, @NonNull Rect region) throws IOException {
        return read(res, id, region, null);
    }

    public static Bitmap read(@NonNull Drawable drawable, @Nullable Bitmap.Config config) {
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

    public static Bitmap read(@NonNull Drawable drawable) {
        return read(drawable, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull Drawable drawable, @NonNull Rect region, @Nullable Bitmap.Config config) {
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

    public static Bitmap read(@NonNull Drawable drawable, @NonNull Rect region) {
        return read(drawable, region, null);
    }

    public static Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri, @Nullable Bitmap.Config config) throws IOException {
        return read(resolver.openInputStream(uri), config);
    }

    public static Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri) throws IOException {
        return read(resolver, uri, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(resolver.openInputStream(uri), region, config);
    }

    public static Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri, @NonNull Rect region) throws IOException {
        return read(resolver, uri, region, null);
    }

    public static Bitmap read(@NonNull URI uri, @Nullable Bitmap.Config config) throws IOException {
        return read(uri.toURL(), config);
    }

    public static Bitmap read(@NonNull URI uri) throws IOException {
        return read(uri, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull URI uri, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(uri.toURL(), region, config);
    }

    public static Bitmap read(@NonNull URI uri, @NonNull Rect region) throws IOException {
        return read(uri, region, null);
    }

    public static Bitmap read(@NonNull URL url, @Nullable Bitmap.Config config) throws IOException {
        return read(url.openStream(), config);
    }

    public static @Nullable Bitmap read(@NonNull URL url) throws IOException {
        return read(url, (Bitmap.Config) null);
    }

    public static Bitmap read(@NonNull URL url, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException {
        return read(url.openStream(), region, config);
    }

    public static Bitmap read(@NonNull URL url, @NonNull Rect region) throws IOException {
        return read(url, region, null);
    }

    public static boolean write(@NonNull File output, @NonNull Bitmap bitmap, @NonNull String formatName, int quality) throws IOException {
        if (output.exists()) {
            if (!output.delete()) return false;
        }
        if (!output.createNewFile()) return false;
        FileOutputStream fos = new FileOutputStream(output);
        return write(fos, bitmap, formatName, quality);
    }

    public static boolean write(@NonNull OutputStream output, @NonNull Bitmap bitmap, @NonNull String formatName, int quality) throws IOException {
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
        output.close();
        return result;
    }

}

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

package io.notcute.g2d.android;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BitmapIO {

    private BitmapIO() {
        throw new UnsupportedOperationException();
    }

    public static Bitmap read(InputStream stream, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(stream, config);
            if (bitmap != null) break;
        }
        Util.closeQuietly(stream);
        return bitmap;
    }

    public static Bitmap read(InputStream stream) throws IOException {
        return read(stream, (Bitmap.Config) null);
    }

    public static Bitmap read(InputStream stream, Rect region, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(stream, region, config);
            if (bitmap != null) break;
        }
        Util.closeQuietly(stream);
        return bitmap;
    }

    public static Bitmap read(InputStream stream, Rect region) throws IOException {
        return read(stream, region, null);
    }

    public static Bitmap read(File file, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(file, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(File file) throws IOException {
        return read(file, (Bitmap.Config) null);
    }

    public static Bitmap read(File file, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(file, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(File file, Rect region) throws IOException {
        return read(file, region, null);
    }

    public static Bitmap read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(descriptor, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(ParcelFileDescriptor descriptor) throws IOException {
        return read(descriptor, (Bitmap.Config) null);
    }

    public static Bitmap read(ParcelFileDescriptor descriptor, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(descriptor, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(ParcelFileDescriptor descriptor, Rect region) throws IOException {
        return read(descriptor, region, null);
    }

    public static Bitmap read(String pathname, Bitmap.Config config) throws IOException {
        return read(new File(pathname), config);
    }

    public static Bitmap read(String pathname) throws IOException {
        return read(pathname, (Bitmap.Config) null);
    }

    public static Bitmap read(String pathname, Rect region, Bitmap.Config config) throws IOException {
        return read(new File(pathname), region, config);
    }

    public static Bitmap read(String pathname, Rect region) throws IOException {
        return read(pathname, region, null);
    }

    public static Bitmap read(byte[] data, Bitmap.Config config) {
        return read(data, 0, data.length, config);
    }

    public static Bitmap read(byte[] data) {
        return read(data, (Bitmap.Config) null);
    }

    public static Bitmap read(byte[] data, int offset, int length, Bitmap.Config config) {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(data, offset, length, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(byte[] data, int offset, int length) {
        return read(data, offset, length, (Bitmap.Config) null);
    }

    public static Bitmap read(byte[] data, Rect region, Bitmap.Config config) {
        return read(data, 0, data.length, region, config);
    }

    public static Bitmap read(byte[] data, Rect region) {
        return read(data, region, null);
    }

    public static Bitmap read(byte[] data, int offset, int length, Rect region, Bitmap.Config config) {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(data, offset, length, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(byte[] data, int offset, int length, Rect region) {
        return read(data, offset, length, region, null);
    }

    public static Bitmap read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(assets, asset, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(AssetManager assets, String asset) throws IOException {
        return read(assets, asset, (Bitmap.Config) null);
    }

    public static Bitmap read(AssetManager assets, String asset, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(assets, asset, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(AssetManager assets, String asset, Rect region) throws IOException {
        return read(assets, asset, region, null);
    }

    @SuppressLint("ResourceType")
    public static Bitmap read(Resources res, int id, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(res, id, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(Resources res, int id) throws IOException {
        return read(res, id, (Bitmap.Config) null);
    }

    @SuppressLint("ResourceType")
    public static Bitmap read(Resources res, int id, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(res, id, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(Resources res, int id, Rect region) throws IOException {
        return read(res, id, region, null);
    }

    public static Bitmap read(Drawable drawable, Bitmap.Config config) {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(drawable, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(Drawable drawable) {
        return read(drawable, (Bitmap.Config) null);
    }

    public static Bitmap read(Drawable drawable, Rect region, Bitmap.Config config) {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(drawable, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(Drawable drawable, Rect region) {
        return read(drawable, region, null);
    }

    public static Bitmap read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(resolver, uri, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(ContentResolver resolver, Uri uri) throws IOException {
        return read(resolver, uri, (Bitmap.Config) null);
    }

    public static Bitmap read(ContentResolver resolver, Uri uri, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(resolver, uri, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(ContentResolver resolver, Uri uri, Rect region) throws IOException {
        return read(resolver, uri, region, null);
    }

    public static Bitmap read(URI uri, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(uri, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(URI uri) throws IOException {
        return read(uri, (Bitmap.Config) null);
    }

    public static Bitmap read(URI uri, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(uri, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(URI uri, Rect region) throws IOException {
        return read(uri, region, null);
    }

    public static Bitmap read(URL url, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(url, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(URL url) throws IOException {
        return read(url, (Bitmap.Config) null);
    }

    public static Bitmap read(URL url, Rect region, Bitmap.Config config) throws IOException {
        Bitmap bitmap = null;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            bitmap = provider.read(url, region, config);
            if (bitmap != null) break;
        }
        return bitmap;
    }

    public static Bitmap read(URL url, Rect region) throws IOException {
        return read(url, region, null);
    }

    public static boolean write(File output, Bitmap bitmap, String formatName, int quality) throws IOException {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            result = provider.write(output, bitmap, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static boolean write(OutputStream output, Bitmap bitmap, String formatName, int quality) throws IOException {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            result = provider.write(output, bitmap, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (BIOServiceProvider provider : BIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}

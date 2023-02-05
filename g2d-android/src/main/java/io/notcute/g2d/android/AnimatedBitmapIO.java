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
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import io.notcute.g2d.AnimatedImage;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public final class AnimatedBitmapIO {

    private AnimatedBitmapIO() {
        throw new UnsupportedOperationException();
    }

    public static AnimatedImage read(InputStream stream, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(stream, config);
            if (image != null) break;
        }
        Util.closeQuietly(stream);
        return image;
    }

    public static AnimatedImage read(InputStream stream) throws IOException {
        return read(stream, (Bitmap.Config) null);
    }

    public static AnimatedImage read(File file, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(file, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(File file) throws IOException {
        return read(file, (Bitmap.Config) null);
    }

    public static AnimatedImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(descriptor, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(ParcelFileDescriptor descriptor) throws IOException {
        return read(descriptor, (Bitmap.Config) null);
    }

    public static AnimatedImage read(String pathname, Bitmap.Config config) throws IOException {
        return read(new File(pathname), config);
    }

    public static AnimatedImage read(String pathname) throws IOException {
        return read(pathname, (Bitmap.Config) null);
    }

    public static AnimatedImage read(byte[] data, Bitmap.Config config) {
        return read(data, 0, data.length, config);
    }

    public static AnimatedImage read(byte[] data) {
        return read(data, (Bitmap.Config) null);
    }

    public static AnimatedImage read(byte[] data, int offset, int length, Bitmap.Config config) {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(data, offset, length, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(byte[] data, int offset, int length) {
        return read(data, offset, length, (Bitmap.Config) null);
    }

    public static AnimatedImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(assets, asset, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(AssetManager assets, String asset) throws IOException {
        return read(assets, asset, (Bitmap.Config) null);
    }

    @SuppressLint("ResourceType")
    public static AnimatedImage read(Resources res, int id, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(res, id, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(Resources res, int id) throws IOException {
        return read(res, id, (Bitmap.Config) null);
    }

    public static AnimatedImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(resolver, uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(ContentResolver resolver, Uri uri) throws IOException {
        return read(resolver, uri, (Bitmap.Config) null);
    }

    public static AnimatedImage read(URI uri, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(URI uri) throws IOException {
        return read(uri, (Bitmap.Config) null);
    }

    public static AnimatedImage read(URL url, Bitmap.Config config) throws IOException {
        AnimatedImage image = null;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            image = provider.read(url, config);
            if (image != null) break;
        }
        return image;
    }

    public static AnimatedImage read(URL url) throws IOException {
        return read(url, (Bitmap.Config) null);
    }

    public static boolean write(File output, AnimatedImage image, String formatName, int quality) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            result = provider.write(output, image, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static boolean write(OutputStream output, AnimatedImage image, String formatName, int quality) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            result = provider.write(output, image, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (ABIOServiceProvider provider : ABIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}

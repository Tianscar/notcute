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

package a3wt.android;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import a3wt.graphics.A3FramedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static a3wt.util.A3Streams.closeQuietly;

/**
 * A factory class that providing functions to decode and encode Android Bitmap.
 */
public final class FramedBitmapIO {

    private FramedBitmapIO(){}

    public static A3FramedImage read(InputStream stream, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(stream, config);
            if (image != null) break;
        }
        closeQuietly(stream);
        return image;
    }

    public static A3FramedImage read(InputStream stream) throws IOException {
        return read(stream, (Bitmap.Config) null);
    }

    public static A3FramedImage read(File file, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(file, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(File file) throws IOException {
        return read(file, (Bitmap.Config) null);
    }

    public static A3FramedImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(descriptor, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(ParcelFileDescriptor descriptor) throws IOException {
        return read(descriptor, (Bitmap.Config) null);
    }

    public static A3FramedImage read(String pathname, Bitmap.Config config) throws IOException {
        return read(new File(pathname), config);
    }

    public static A3FramedImage read(String pathname) throws IOException {
        return read(pathname, (Bitmap.Config) null);
    }

    public static A3FramedImage read(byte[] data, Bitmap.Config config) {
        return read(data, 0, data.length, config);
    }

    public static A3FramedImage read(byte[] data) {
        return read(data, (Bitmap.Config) null);
    }

    public static A3FramedImage read(byte[] data, int offset, int length, Bitmap.Config config) {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(data, offset, length, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(byte[] data, int offset, int length) {
        return read(data, offset, length, (Bitmap.Config) null);
    }

    public static A3FramedImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(assets, asset, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(AssetManager assets, String asset) throws IOException {
        return read(assets, asset, (Bitmap.Config) null);
    }

    @SuppressLint("ResourceType")
    public static A3FramedImage read(Resources res, int id, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(res, id, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(Resources res, int id) throws IOException {
        return read(res, id, (Bitmap.Config) null);
    }

    public static A3FramedImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(resolver, uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(ContentResolver resolver, Uri uri) throws IOException {
        return read(resolver, uri, (Bitmap.Config) null);
    }

    public static A3FramedImage read(URI uri, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(URI uri) throws IOException {
        return read(uri, (Bitmap.Config) null);
    }

    public static A3FramedImage read(URL url, Bitmap.Config config) throws IOException {
        A3FramedImage image = null;
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            image = provider.read(url, config);
            if (image != null) break;
        }
        return image;
    }

    public static A3FramedImage read(URL url) throws IOException {
        return read(url, (Bitmap.Config) null);
    }

    public static boolean write(File output, A3FramedImage image, String formatName, int quality) throws IOException {
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
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            result = provider.write(output, image, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static boolean write(OutputStream output, A3FramedImage image, String formatName, int quality) throws IOException {
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
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            result = provider.write(output, image, formatName, quality);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (FBIOServiceProvider provider : FBIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}

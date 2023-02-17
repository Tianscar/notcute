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
import io.notcute.g2d.MultiFrameImage;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public final class MultiFrameBitmapIO {

    private MultiFrameBitmapIO() {
        throw new UnsupportedOperationException();
    }

    public static MultiFrameImage read(InputStream stream, Bitmap.Config config) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(stream, config);
            if (image != null) break;
        }
        Util.closeQuietly(stream);
        return image;
    }

    public static MultiFrameImage read(InputStream stream) throws IOException {
        return read(stream, null);
    }

    public static MultiFrameImage read(File file, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(file, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(File file) throws IOException {
        return read(file, null);
    }

    public static MultiFrameImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(descriptor, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(ParcelFileDescriptor descriptor) throws IOException {
        return read(descriptor, null);
    }

    public static MultiFrameImage read(String pathname, Bitmap.Config config) throws IOException {
        return read(new File(pathname), config);
    }

    public static MultiFrameImage read(String pathname) throws IOException {
        return read(pathname, null);
    }

    public static MultiFrameImage read(byte[] data, Bitmap.Config config) {
        return read(data, 0, data.length, config);
    }

    public static MultiFrameImage read(byte[] data) {
        return read(data, null);
    }

    public static MultiFrameImage read(byte[] data, int offset, int length, Bitmap.Config config) {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(data, offset, length, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(byte[] data, int offset, int length) {
        return read(data, offset, length, null);
    }

    public static MultiFrameImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(assets, asset, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(AssetManager assets, String asset) throws IOException {
        return read(assets, asset, null);
    }

    @SuppressLint("ResourceType")
    public static MultiFrameImage read(Resources res, int id, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(res, id, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(Resources res, int id) throws IOException {
        return read(res, id, null);
    }

    public static MultiFrameImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(resolver, uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(ContentResolver resolver, Uri uri) throws IOException {
        return read(resolver, uri, null);
    }

    public static MultiFrameImage read(URI uri, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(uri, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(URI uri) throws IOException {
        return read(uri, null);
    }

    public static MultiFrameImage read(URL url, Bitmap.Config config) throws IOException {
        MultiFrameImage image = null;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            image = provider.read(url, config);
            if (image != null) break;
        }
        return image;
    }

    public static MultiFrameImage read(URL url) throws IOException {
        return read(url, null);
    }

    public static boolean write(File output, MultiFrameImage image, String mimeType, int quality) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (mimeType == null) {
            throw new IllegalArgumentException("MIME Type cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            result = provider.write(output, image, mimeType, quality);
            if (result) break;
        }
        return result;
    }

    public static boolean write(OutputStream output, MultiFrameImage image, String mimeType, int quality) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (mimeType == null) {
            throw new IllegalArgumentException("MIME Type cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            result = provider.write(output, image, mimeType, quality);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public static String[] getWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (MFBIOServiceProvider provider : MFBIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

}

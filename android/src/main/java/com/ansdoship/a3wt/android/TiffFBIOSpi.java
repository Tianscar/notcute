package com.ansdoship.a3wt.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.ansdoship.a3wt.graphics.A3FramedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class TiffFBIOSpi implements FBIOServiceProvider {

    @Override
    public A3FramedImage read(InputStream stream, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(File file, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(byte[] data, int offset, int length, Bitmap.Config config) {
        return null;
    }

    @Override
    public A3FramedImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(Resources res, int id, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(URI uri, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public A3FramedImage read(URL url, Bitmap.Config config) throws IOException {
        return null;
    }

    @Override
    public boolean write(File output, A3FramedImage im, String formatName, int quality) throws IOException {
        return false;
    }

    @Override
    public boolean write(OutputStream output, A3FramedImage im, String formatName, int quality) throws IOException {
        return false;
    }

    @Override
    public String[] getReaderFormatNames() {
        return new String[0];
    }

    @Override
    public String[] getWriterFormatNames() {
        return new String[0];
    }

}

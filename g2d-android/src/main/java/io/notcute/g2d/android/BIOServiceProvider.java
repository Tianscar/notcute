package io.notcute.g2d.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public interface BIOServiceProvider {

    Bitmap read(InputStream stream, Bitmap.Config config) throws IOException;
    Bitmap read(InputStream stream, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(File file, Bitmap.Config config) throws IOException;
    Bitmap read(File file, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException;
    Bitmap read(ParcelFileDescriptor descriptor, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(byte[] data, int offset, int length, Bitmap.Config config);
    Bitmap read(byte[] data, int offset, int length, Rect region, Bitmap.Config config);
    Bitmap read(AssetManager assets, String asset, Bitmap.Config config) throws IOException;
    Bitmap read(AssetManager assets, String asset, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(Resources res, int id, Bitmap.Config config) throws IOException;
    Bitmap read(Resources res, int id, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(Drawable drawable, Bitmap.Config config);
    Bitmap read(Drawable drawable, Rect region, Bitmap.Config config);
    Bitmap read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException;
    Bitmap read(ContentResolver resolver, Uri uri, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(URI uri, Bitmap.Config config) throws IOException;
    Bitmap read(URI uri, Rect region, Bitmap.Config config) throws IOException;
    Bitmap read(URL url, Bitmap.Config config) throws IOException;
    Bitmap read(URL url, Rect region, Bitmap.Config config) throws IOException;

    boolean write(File output, Bitmap bitmap, String formatName, int quality) throws IOException;
    boolean write(OutputStream output, Bitmap bitmap, String formatName, int quality) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

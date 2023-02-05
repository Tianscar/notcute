package io.notcute.g2d.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import io.notcute.g2d.AnimatedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public interface ABIOServiceProvider {

    AnimatedImage read(InputStream stream, Bitmap.Config config) throws IOException;
    AnimatedImage read(File file, Bitmap.Config config) throws IOException;
    AnimatedImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException;
    AnimatedImage read(byte[] data, int offset, int length, Bitmap.Config config);
    AnimatedImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException;
    AnimatedImage read(Resources res, int id, Bitmap.Config config) throws IOException;
    AnimatedImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException;
    AnimatedImage read(URI uri, Bitmap.Config config) throws IOException;
    AnimatedImage read(URL url, Bitmap.Config config) throws IOException;

    boolean write(File output, AnimatedImage im, String formatName, int quality) throws IOException;
    boolean write(OutputStream output, AnimatedImage im, String formatName, int quality) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

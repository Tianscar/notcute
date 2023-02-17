package io.notcute.g2d.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import io.notcute.g2d.MultiFrameImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public interface MFBIOServiceProvider {

    MultiFrameImage read(InputStream stream, Bitmap.Config config) throws IOException;
    MultiFrameImage read(File file, Bitmap.Config config) throws IOException;
    MultiFrameImage read(ParcelFileDescriptor descriptor, Bitmap.Config config) throws IOException;
    MultiFrameImage read(byte[] data, int offset, int length, Bitmap.Config config);
    MultiFrameImage read(AssetManager assets, String asset, Bitmap.Config config) throws IOException;
    MultiFrameImage read(Resources res, int id, Bitmap.Config config) throws IOException;
    MultiFrameImage read(ContentResolver resolver, Uri uri, Bitmap.Config config) throws IOException;
    MultiFrameImage read(URI uri, Bitmap.Config config) throws IOException;
    MultiFrameImage read(URL url, Bitmap.Config config) throws IOException;

    boolean write(File output, MultiFrameImage im, String mimeType, int quality) throws IOException;
    boolean write(OutputStream output, MultiFrameImage im, String mimeType, int quality) throws IOException;

    String[] getReaderMIMETypes();
    String[] getWriterMIMETypes();

}

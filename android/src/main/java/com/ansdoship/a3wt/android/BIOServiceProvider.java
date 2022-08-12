package com.ansdoship.a3wt.android;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public interface BIOServiceProvider {

    Bitmap read(@NonNull InputStream stream, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull InputStream stream, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull File file, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull File file, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull byte[] data, int offset, int length, @Nullable Bitmap.Config config);
    Bitmap read(@NonNull byte[] data, int offset, int length, @NonNull Rect region, @Nullable Bitmap.Config config);
    Bitmap read(@NonNull AssetManager assets, @NonNull String asset, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull AssetManager assets, @NonNull String asset, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull Resources res, int id, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull Resources res, int id, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull Drawable drawable, @Nullable Bitmap.Config config);
    Bitmap read(@NonNull Drawable drawable, @NonNull Rect region, @Nullable Bitmap.Config config);
    Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull ContentResolver resolver, @NonNull Uri uri, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull URI uri, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull URI uri, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull URL url, @Nullable Bitmap.Config config) throws IOException;
    Bitmap read(@NonNull URL url, @NonNull Rect region, @Nullable Bitmap.Config config) throws IOException;

    boolean write(@NonNull File output, @NonNull Bitmap bitmap, @NonNull String formatName, int quality) throws IOException;
    boolean write(@NonNull OutputStream output, @NonNull Bitmap bitmap, @NonNull String formatName, int quality) throws IOException;

    String[] getReaderFormatNames();
    String[] getWriterFormatNames();

}

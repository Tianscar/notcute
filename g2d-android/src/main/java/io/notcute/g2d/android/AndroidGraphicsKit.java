package io.notcute.g2d.android;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import io.notcute.app.Assets;
import io.notcute.app.android.AndroidAssets;
import io.notcute.g2d.AnimatedImage;
import io.notcute.g2d.Font;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AndroidGraphicsKit implements GraphicsKit {

    @Override
    public Image createImage(int width, int height, int type) {
        return new AndroidImage(Bitmap.createBitmap(width, height, Util.toAndroidBitmapConfig(type)));
    }

    @Override
    public Image readImage(File input, int type) {
        try {
            AnimatedImage framedImage = readAnimatedImage(input, type);
            if (framedImage != null) return framedImage;
            Bitmap bitmap = BitmapIO.read(input, Util.toAndroidBitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidImage(bitmap);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(InputStream input, int type) {
        try {
            AnimatedImage framedImage = readAnimatedImage(input, type);
            if (framedImage != null) return framedImage;
            Bitmap bitmap = BitmapIO.read(input, Util.toAndroidBitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidImage(bitmap);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(URL input, int type) {
        try {
            AnimatedImage framedImage = readAnimatedImage(input, type);
            if (framedImage != null) return framedImage;
            Bitmap bitmap = BitmapIO.read(input, Util.toAndroidBitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidImage(bitmap);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(Assets assets, String input, int type) {
        Objects.requireNonNull(input);
        try {
            AnimatedImage framedImage = readAnimatedImage(assets, Util.removeStartSeparator(input), type);
            if (framedImage != null) return framedImage;
            Bitmap bitmap = BitmapIO.read(((AndroidAssets) assets).getAssets(), Util.removeStartSeparator(input), Util.toAndroidBitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidImage(bitmap);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(File input, int type) {
        try {
            return AnimatedBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(InputStream input, int type) {
        try {
            return AnimatedBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(URL input, int type) {
        try {
            return AnimatedBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(Assets assets, String input, int type) {
        Objects.requireNonNull(input);
        try {
            return AnimatedBitmapIO.read(((AndroidAssets) assets).getAssets(), Util.removeStartSeparator(input), Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, File output) {
        try {
            if (image instanceof AnimatedImage) return writeAnimatedImage((AnimatedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidImage)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, OutputStream output) {
        try {
            if (image instanceof AnimatedImage) return writeAnimatedImage((AnimatedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidImage)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, File output) {
        try {
            return AnimatedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, OutputStream output) {
        try {
            return AnimatedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (String formatName : AnimatedBitmapIO.getReaderFormatNames()) {
            formatNames.remove(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (String formatName : AnimatedBitmapIO.getWriterFormatNames()) {
            formatNames.remove(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : AnimatedBitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : AnimatedBitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public Font getFont(String familyName, int style) {
        Objects.requireNonNull(familyName);
        return new AndroidFont(Typeface.create(familyName, Util.toAndroidTypefaceStyle(style)));
    }

    @Override
    public Font getDefaultFont() {
        return AndroidFont.DEFAULT_FONT;
    }

    @Override
    public Font readFont(File input) {
        try {
            Typeface typeface = Util.readTypeface(input);
            return typeface == null ? null : new AndroidFont(typeface);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Font readFont(Assets assets, String input) {
        Objects.requireNonNull(input);
        try {
            Typeface typeface = Util.readTypeface(((AndroidAssets) assets).getAssets(), Util.removeStartSeparator(input));
            return typeface == null ? null : new AndroidFont(typeface);
        } catch (IOException e) {
            return null;
        }
    }
    
}

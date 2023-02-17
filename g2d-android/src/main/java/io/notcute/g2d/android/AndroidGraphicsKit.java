package io.notcute.g2d.android;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import io.notcute.app.Assets;
import io.notcute.app.android.AndroidAssets;
import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Font;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

public class AndroidGraphicsKit implements GraphicsKit {

    @Override
    public Image createImage(int width, int height, int type) {
        return new AndroidImage(Bitmap.createBitmap(width, height, Util.toAndroidBitmapConfig(type)));
    }

    @Override
    public Image readImage(File input, int type) {
        try {
            MultiFrameImage framedImage = readMultiFrameImage(input, type);
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
            MultiFrameImage framedImage = readMultiFrameImage(input, type);
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
            MultiFrameImage framedImage = readMultiFrameImage(input, type);
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
            MultiFrameImage framedImage = readMultiFrameImage(assets, Util.removeStartSeparator(input), type);
            if (framedImage != null) return framedImage;
            Bitmap bitmap = BitmapIO.read(((AndroidAssets) assets).getAssets(), Util.removeStartSeparator(input), Util.toAndroidBitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidImage(bitmap);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(File input, int type) {
        try {
            return MultiFrameBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(InputStream input, int type) {
        try {
            return MultiFrameBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(URL input, int type) {
        try {
            return MultiFrameBitmapIO.read(input, Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(Assets assets, String input, int type) {
        Objects.requireNonNull(input);
        try {
            return MultiFrameBitmapIO.read(((AndroidAssets) assets).getAssets(), Util.removeStartSeparator(input), Util.toAndroidBitmapConfig(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean writeImage(Image image, String mimeType, int quality, File output) {
        try {
            if (image instanceof MultiFrameImage) return writeMultiFrameImage((MultiFrameImage) image, mimeType, quality, output);
            return BitmapIO.write(output, ((AndroidImage)image).getBitmap(), mimeType, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String mimeType, int quality, OutputStream output) {
        try {
            if (image instanceof MultiFrameImage) return writeMultiFrameImage((MultiFrameImage) image, mimeType, quality, output);
            return BitmapIO.write(output, ((AndroidImage)image).getBitmap(), mimeType, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, File output) {
        try {
            return MultiFrameBitmapIO.write(output, image, mimeType, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, OutputStream output) {
        try {
            return MultiFrameBitmapIO.write(output, image, mimeType, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderMIMETypes() {
        return BitmapIO.getReaderMIMETypes();
    }

    @Override
    public String[] getImageWriterMIMETypes() {
        return BitmapIO.getWriterMIMETypes();
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        return MultiFrameBitmapIO.getReaderMIMETypes();
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        return MultiFrameBitmapIO.getWriterMIMETypes();
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

package a3wt.android;

import a3wt.graphics.*;
import android.content.Context;
import android.graphics.*;
import a3wt.app.A3Assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static a3wt.util.A3Files.removeStartSeparator;
import static a3wt.util.A3Preconditions.checkArgNotEmpty;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3GraphicsKit implements A3GraphicsKit {

    protected final AtomicReference<Context> contextRef = new AtomicReference<>();

    public void attach(final Context context) {
        checkArgNotNull(context, "context");
        contextRef.compareAndSet(null, context);
    }

    public void detach() {
        contextRef.set(null);
    }

    @Override
    public A3Image createImage(final int width, final int height, final int type) {
        return new AndroidA3Image(Bitmap.createBitmap(width, height, A3AndroidUtils.imageType2BitmapConfig(type)));
    }

    @Override
    public A3Image readImage(final File input, final int type) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input, type);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input, final int type) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input, type);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input, final int type) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input, type);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input, final int type) {
        checkArgNotNull(assets, "assets");
        checkArgNotEmpty(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(assets, removeStartSeparator(input), type);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(((AndroidA3Assets)assets).assets, removeStartSeparator(input), A3AndroidUtils.imageType2BitmapConfig(type));
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final File input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final InputStream input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final URL input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input, A3AndroidUtils.imageType2BitmapConfig(type));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final A3Assets assets, final String input, final int type) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(((AndroidA3Assets)assets).assets, removeStartSeparator(input), A3AndroidUtils.imageType2BitmapConfig(type));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try {
            if (image instanceof A3FramedImage) return writeFramedImage((A3FramedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            if (image instanceof A3FramedImage) return writeFramedImage((A3FramedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try {
            return FramedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            return FramedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (final String formatName : BitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedBitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (final String formatName : BitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedBitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public A3Transform createTransform() {
        return new AndroidA3Transform(new Matrix());
    }

    @Override
    public A3Transform createTransform(final float[] matrixValues) {
        return new AndroidA3Transform(new Matrix()).setMatrixValues(matrixValues);
    }

    @Override
    public A3Transform createTransform(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        return new AndroidA3Transform(new Matrix()).set(sx, kx, dx, ky, sy, dy);
    }

    @Override
    public A3Transform createTransform(final A3Point scale, final A3Point skew, final A3Point translate) {
        checkArgNotNull(scale, "scale");
        checkArgNotNull(skew, "skew");
        checkArgNotNull(translate, "translate");
        return new AndroidA3Transform(new Matrix()).set(scale.getX(), skew.getX(), translate.getX(), skew.getY(), scale.getY(), translate.getY());
    }

    @Override
    public A3Path createPath() {
        return new AndroidA3Path(new Path());
    }

    @Override
    public A3Arc createArc() {
        return new AndroidA3Arc();
    }

    @Override
    public A3Arc createArc(final boolean useCenter) {
        return new AndroidA3Arc().setUseCenter(useCenter);
    }

    @Override
    public A3Arc createArc(final float x, final float y, final float width, final float height,
                           final float startAngle, final float sweepAngle, final boolean useCenter) {
        return new AndroidA3Arc(x, y, width, height, startAngle, sweepAngle, useCenter);
    }

    @Override
    public A3Arc createArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return createArc(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), startAngle, sweepAngle, useCenter);
    }

    @Override
    public A3Arc createArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(rect, "rect");
        return createArc(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), startAngle, sweepAngle, useCenter);
    }

    @Override
    public A3Line createLine() {
        return new AndroidA3Line();
    }

    @Override
    public A3Line createLine(final float startX, final float startY, final float endX, final float endY) {
        return new AndroidA3Line(startX, startY, endX, endY);
    }

    @Override
    public A3Line createLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        return createLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3QuadCurve createQuadCurve() {
        return new AndroidA3QuadCurve();
    }

    @Override
    public A3QuadCurve createQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        return new AndroidA3QuadCurve(startX, startY, ctrlX, ctrlY, endX, endY);
    }

    @Override
    public A3QuadCurve createQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        return createQuadCurve(startPos.getX(), startPos.getY(), ctrlPos.getX(), ctrlPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3CubicCurve createCubicCurve() {
        return new AndroidA3CubicCurve();
    }

    @Override
    public A3CubicCurve createCubicCurve(final float startX, final float startY, final float ctrlX1, final float ctrlY1,
                                         final float ctrlX2, final float ctrlY2, final float endX, final float endY) {
        return new AndroidA3CubicCurve(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
    }

    @Override
    public A3CubicCurve createCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos1, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        return createCubicCurve(startPos.getX(), startPos.getY(), ctrlPos1.getX(), ctrlPos1.getY(), ctrlPos2.getX(), ctrlPos2.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3Point createPoint() {
        return new AndroidA3Point(new PointF());
    }

    @Override
    public A3Point createPoint(final float x, final float y) {
        return new AndroidA3Point(new PointF(x, y));
    }

    @Override
    public A3Oval createOval() {
        return new AndroidA3Oval();
    }

    @Override
    public A3Oval createOval(final float x, final float y, final float width, final float height) {
        return new AndroidA3Oval(x, y, width, height);
    }

    @Override
    public A3Oval createOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return createOval(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Oval createOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return createOval(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Rect createRect() {
        return new AndroidA3Rect(new RectF());
    }

    @Override
    public A3Rect createRect(final float x, final float y, final float width, final float height) {
        return new AndroidA3Rect(new RectF(x, y, width, height));
    }

    @Override
    public A3Rect createRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return createRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3RoundRect createRoundRect() {
        return new AndroidA3RoundRect();
    }

    @Override
    public A3RoundRect createRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        return new AndroidA3RoundRect(x, y, width, height, rx, ry);
    }

    @Override
    public A3RoundRect createRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        return createRoundRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), corner.getWidth(), corner.getHeight());
    }

    @Override
    public A3RoundRect createRoundRect(final A3Rect rect, final A3Size corner) {
        checkArgNotNull(rect, "rect");
        checkArgNotNull(corner, "corner");
        return createRoundRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), corner.getWidth(), corner.getHeight());
    }

    @Override
    public A3Size createSize() {
        return new AndroidA3Size();
    }

    @Override
    public A3Size createSize(final float width, final float height) {
        return new AndroidA3Size(width, height);
    }

    @Override
    public A3Font readFont(final File input) {
        checkArgNotNull(input, "input");
        try {
            final Typeface typeface = A3AndroidUtils.readTypeface(input);
            return typeface == null ? null : new AndroidA3Font(typeface);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotEmpty(input, "input");
        try {
            final Typeface typeface = A3AndroidUtils.readTypeface(((AndroidA3Assets)assets).getAssets(), removeStartSeparator(input));
            return typeface == null ? null : new AndroidA3Font(typeface);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final String familyName, final int style) {
        checkArgNotEmpty(familyName, "familyName");
        return new AndroidA3Font(Typeface.create(familyName, A3AndroidUtils.fontStyle2TypefaceStyle(style)));
    }

    protected static final AndroidA3Font DEFAULT_FONT = new AndroidA3Font(Typeface.DEFAULT);

    @Override
    public A3Font getDefaultFont() {
        return DEFAULT_FONT;
    }

    @Override
    public A3Cursor createCursor(final int type) {
        return new AndroidA3Cursor(contextRef.get(), type);
    }

    @Override
    public A3Cursor createCursor(final A3Image image, final float hotSpotX, final float hotSpotY) {
        if (image instanceof A3FramedImage) return createFramedCursor((A3FramedImage) image, hotSpotX, hotSpotY);
        else return new AndroidA3Cursor(((AndroidA3Image) image), hotSpotX, hotSpotY);
    }

    @Override
    public A3Cursor createCursor(final A3Image image, final A3Point hotSpot) {
        if (image instanceof A3FramedImage) return createFramedCursor((A3FramedImage) image, hotSpot);
        else return new AndroidA3Cursor(((AndroidA3Image) image), hotSpot.getX(), hotSpot.getY());
    }

    @Override
    public A3Cursor createFramedCursor(final A3FramedImage image, final float hotSpotX, final float hotSpotY) {
        final A3Cursor.Frame[] frames = new A3Cursor.Frame[image.size()];
        for (int i = 0; i < frames.length; i ++) {
            frames[i] = new A3Cursor.DefaultFrame(new AndroidA3Cursor((AndroidA3Image) image.get(i).getImage(), hotSpotX, hotSpotY),
                    image.get(i).getDuration());
        }
        return createFramedCursor(frames);
    }

    @Override
    public A3Cursor createFramedCursor(final A3FramedImage image, final A3Point hotSpot) {
        return createFramedCursor(image, hotSpot.getX(), hotSpot.getY());
    }

    @Override
    public A3Cursor getDefaultCursor() {
        return new AndroidA3Cursor(contextRef.get(), A3Cursor.Type.DEFAULT);
    }

}

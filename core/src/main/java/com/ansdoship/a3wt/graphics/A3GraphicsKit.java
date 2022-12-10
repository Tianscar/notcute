package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

public interface A3GraphicsKit {

    A3Image createImage(final int width, final int height, final int type);
    default A3FramedImage createFramedImage(final A3Image... frames) {
        return new DefaultA3FramedImage(frames);
    }
    default A3FramedImage createFramedImage(final Collection<A3Image> frames) {
        return new DefaultA3FramedImage(frames);
    }
    default A3FramedImage createFramedImage(final Iterator<A3Image> frames) {
        return new DefaultA3FramedImage(frames);
    }

    A3Image readImage(final File input, final int type);
    A3Image readImage(final InputStream input, final int type);
    A3Image readImage(final URL input, final int type);
    A3Image readImage(final A3Assets assets, final String input, final int type);
    A3FramedImage readFramedImage(final File input, final int type);
    A3FramedImage readFramedImage(final InputStream input, final int type);
    A3FramedImage readFramedImage(final URL input, final int type);
    A3FramedImage readFramedImage(final A3Assets assets, final String input, final int type);

    boolean writeImage(final A3Image image, final String formatName, final int quality, final File output);
    boolean writeImage(final A3Image image, final String formatName, final int quality, final OutputStream output);
    boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final File output);
    boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final OutputStream output);

    String[] getImageReaderFormatNames();
    String[] getImageWriterFormatNames();

    default A3Graphics.Data createGraphicsData() {
        return new A3Graphics.DefaultData();
    }

    default A3Font.Metrics createFontMetrics() {
        return new A3Font.DefaultMetrics();
    }
    default A3Font.Metrics createFontMetrics(final float baseline, final float ascent,
                                             final float descent, final float leading,
                                             final float top, final float bottom) {
        return new A3Font.DefaultMetrics(baseline, ascent, descent, leading, top, bottom);
    }

    A3Transform createTransform();
    A3Coordinate createCoordinate(final int x, final int y);
    A3Dimension createDimension(final int width, final int height);
    A3Area createArea(final int left, final int top, final int right, final int bottom);
    A3Path createPath();
    A3Arc createArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc createArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Line createLine(final float startX, final float startY, final float endX, final float endY);
    A3Line createLine(final A3Point startPos, final A3Point endPos);
    A3QuadCurve createQuadCurve(final float startX, final float startY, final float endX, final float endY, final float ctrlX, final float ctrlY);
    A3QuadCurve createQuadCurve(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos);
    A3CubicCurve createCubicCurve(final float startX, final float startY, final float endX, final float endY,
                                  final float ctrlX1, final float ctrlY1, final float ctrlX2, final float ctrlY2);
    A3QuadCurve createQuadCurve(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos1, final A3Point ctrlPos2);
    A3Point createPoint(final float x, final float y);
    A3Oval createOval(final float x, final float y, final float width, final float height);
    A3Oval createOval(final A3Rect rect);
    A3Rect createRect(final float x, final float y, final float width, final float height);
    A3RoundRect createRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry);
    A3RoundRect createRoundRect(final A3Rect rect, final A3Size corner);
    A3Size createSize(final float width, final float height);

    A3Font readFont(final File input);
    A3Font readFont(final A3Assets assets, final String input);
    A3Font readFont(final String familyName, final int style);

    A3Font getDefaultFont();

    A3Cursor createCursor(final int type);
    A3Cursor createCursor(final A3Image image);

    A3Cursor getDefaultCursor();

}

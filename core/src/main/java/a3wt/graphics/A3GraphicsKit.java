package a3wt.graphics;

import a3wt.app.A3Assets;

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
    A3Transform createTransform(final float[] matrixValues);
    A3Transform createTransform(final float sx, final float kx, final float dx,
                                final float ky, final float sy, final float dy);
    A3Transform createTransform(final A3Point scale, final A3Point skew, final A3Point translate);
    A3Coordinate createCoordinate();
    A3Coordinate createCoordinate(final int x, final int y);
    A3Dimension createDimension();
    A3Dimension createDimension(final int width, final int height);
    A3Area createArea();
    A3Area createArea(final int x, final int y, final int width, final int height);
    A3Area createArea(final A3Coordinate pos, final A3Dimension size);
    A3Path createPath();
    A3Arc createArc();
    A3Arc createArc(final boolean useCenter);
    A3Arc createArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc createArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc createArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Line createLine();
    A3Line createLine(final float startX, final float startY, final float endX, final float endY);
    A3Line createLine(final A3Point startPos, final A3Point endPos);
    A3QuadCurve createQuadCurve();
    A3QuadCurve createQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY);
    A3QuadCurve createQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos);
    A3CubicCurve createCubicCurve();
    A3CubicCurve createCubicCurve(final float startX, final float startY,
                                  final float ctrlX1, final float ctrlY1,
                                  final float ctrlX2, final float ctrlY2,
                                  final float endX, final float endY);
    A3CubicCurve createCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos);
    A3Point createPoint();
    A3Point createPoint(final float x, final float y);
    A3Oval createOval();
    A3Oval createOval(final float x, final float y, final float width, final float height);
    A3Oval createOval(final A3Point pos, final A3Size size);
    A3Oval createOval(final A3Rect rect);
    A3Rect createRect();
    A3Rect createRect(final float x, final float y, final float width, final float height);
    A3Rect createRect(final A3Point pos, final A3Size size);
    A3RoundRect createRoundRect();
    A3RoundRect createRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry);
    A3RoundRect createRoundRect(final A3Point pos, final A3Size size, final A3Size corner);
    A3RoundRect createRoundRect(final A3Rect rect, final A3Size corner);
    A3Size createSize();
    A3Size createSize(final float width, final float height);

    A3Font readFont(final File input);
    A3Font readFont(final A3Assets assets, final String input);
    A3Font readFont(final String familyName, final int style);

    A3Font getDefaultFont();

    A3Cursor createCursor(final int type);
    A3Cursor createCursor(final A3Image image);

    A3Cursor getDefaultCursor();

}

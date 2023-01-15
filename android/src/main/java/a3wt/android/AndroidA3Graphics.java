package a3wt.android;

import a3wt.graphics.*;
import android.graphics.*;
import a3wt.util.A3TextUtils;

import static a3wt.android.A3AndroidUtils.paintStyle2Style;
import static a3wt.android.A3AndroidUtils.style2PaintStyle;
import static a3wt.android.A3AndroidUtils.paintStrokeJoin2StrokeJoin;
import static a3wt.android.A3AndroidUtils.strokeJoin2PaintStrokeJoin;
import static a3wt.android.A3AndroidUtils.paintStrokeCap2StrokeCap;
import static a3wt.android.A3AndroidUtils.strokeCap2PaintStrokeCap;
import static a3wt.util.A3Preconditions.checkArgNotEmpty;
import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgArrayLengthMin;

public class AndroidA3Graphics implements A3Graphics {

    protected final RectF mRectF = new RectF();
    protected final Path mPath = new Path();
    protected volatile RectF mClipRect;
    protected volatile Path mClipPath;
    protected final Matrix mTransformMatrix = new Matrix();
    protected final Matrix mImageMatrix = new Matrix();

    protected volatile Canvas canvas;
    protected final Paint paint = new Paint();
    protected volatile boolean disposed = false;
    protected volatile int width;
    protected volatile int height;
    protected final Data data = new DefaultData();
    protected final Data cacheData = new DefaultData();

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void drawColor() {
        canvas.drawColor(paint.getColor());
    }

    public AndroidA3Graphics(final Bitmap bitmap) {
        this(new Canvas(bitmap), bitmap.getWidth(), bitmap.getHeight());
    }

    public AndroidA3Graphics(final Canvas canvas, final int width, final int height) {
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        reset();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(final Canvas canvas, final int width, final int height) {
        checkDisposed("Can't call setCanvas() on a disposed AndroidA3Graphics");
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        apply();
    }

    public Paint getPaint() {
        return paint;
    }

    public void drawPath(final Path path) {
        checkArgNotNull(path, "path");
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        canvas.save();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    @Override
    public void drawPath(final A3Path path) {
        checkArgNotNull(path, "path");
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        canvas.save();
        canvas.drawPath(((AndroidA3Path)path).getPath(), paint);
        canvas.restore();
    }

    @Override
    public void drawImage(final A3Image image, final float x, final float y) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        canvas.save();
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), x, y, paint);
        canvas.restore();
    }

    @Override
    public void drawImage(final A3Image image, final A3Point point) {
        checkArgNotNull(point, "point");
        drawImage(image, point.getX(), point.getY());
    }

    @Override
    public void drawImage(final A3Image image, final A3Transform transform) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        canvas.save();
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), ((AndroidA3Transform)transform).matrix, paint);
        canvas.restore();
    }

    @Override
    public void drawImage(final A3Image image, final float[] matrixValues) {
        checkArgNotNull(image, "image");
        checkArgArrayLengthMin(matrixValues, AndroidA3Transform.MATRIX_VALUES_LENGTH, true);
        final float[] values = new float[AndroidA3Transform.ANDROID_MATRIX_VALUES_LENGTH];
        System.arraycopy(matrixValues, 0, values, 0, AndroidA3Transform.MATRIX_VALUES_LENGTH);
        values[6] = 0;
        values[7] = 0;
        values[8] = 1;
        mImageMatrix.setValues(values);
        canvas.save();
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), mImageMatrix, paint);
        canvas.restore();
    }

    @Override
    public void drawImage(final A3Image image, final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        checkArgNotNull(image, "image");
        mImageMatrix.setValues(new float[] {sx, kx, dx, ky, sy, dy, 0, 0, 1});
        canvas.save();
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), mImageMatrix, paint);
        canvas.restore();
    }

    @Override
    public void drawPoint(final float x, final float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        canvas.save();
        canvas.drawPoint(x, y, paint);
        canvas.restore();
    }

    @Override
    public void drawPoint(final A3Point point) {
        checkArgNotNull(point, "point");
        drawPoint(point.getX(), point.getY());
    }

    @Override
    public void drawArc(final float x, final float y, final float width, final float height,
                        final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        canvas.save();
        mRectF.set(x, y, x + width, y + height);
        canvas.drawArc(mRectF, startAngle, sweepAngle, useCenter, paint);
        canvas.restore();
    }

    @Override
    public void drawArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        drawArc(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), startAngle, sweepAngle, useCenter);
    }

    @Override
    public void drawArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        canvas.save();
        canvas.drawArc(((AndroidA3Rect)rect).rectF, startAngle, sweepAngle, useCenter, paint);
        canvas.restore();
    }

    @Override
    public void drawArc(final A3Arc arc) {
        checkArgNotNull(arc, "arc");
        drawArc(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc.getStartAngle(), arc.getSweepAngle(), arc.isUseCenter());
    }

    @Override
    public void drawLine(final float startX, final float startY, final float endX, final float endY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        canvas.save();
        canvas.drawLine(startX, startY, endX, endY, paint);
        canvas.restore();
    }

    @Override
    public void drawLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        drawLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public void drawLine(final A3Line line) {
        checkArgNotNull(line, "line");
        drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    @Override
    public void drawQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        checkDisposed("Can't call drawQuadCurve() on a disposed A3Graphics");
        canvas.save();
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.quadTo(ctrlX, ctrlY, endX, endY);
        canvas.drawPath(mPath, paint);
        canvas.restore();
    }

    @Override
    public void drawQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        drawQuadCurve(startPos.getX(), startPos.getY(), ctrlPos.getX(), ctrlPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public void drawQuadCurve(final A3QuadCurve quadCurve) {
        checkArgNotNull(quadCurve, "quadCurve");
        drawQuadCurve(quadCurve.getStartX(), quadCurve.getStartY(), quadCurve.getCtrlX(), quadCurve.getCtrlY(), quadCurve.getEndX(), quadCurve.getEndY());
    }

    @Override
    public void drawCubicCurve(final float startX, final float startY, final float ctrlX1, final float ctrlY1,
                               final float ctrlX2, final float ctrlY2, final float endX, final float endY) {
        checkDisposed("Can't call drawCubicCurve() on a disposed A3Graphics");
        canvas.save();
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.cubicTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
        canvas.drawPath(mPath, paint);
        canvas.restore();
    }

    @Override
    public void drawCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        drawCubicCurve(startPos.getX(), startPos.getY(), ctrlPos1.getX(), ctrlPos1.getY(), ctrlPos2.getX(), ctrlPos2.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public void drawCubicCurve(final A3CubicCurve cubicCurve) {
        checkArgNotNull(cubicCurve, "cubicCurve");
        drawCubicCurve(cubicCurve.getStartX(), cubicCurve.getStartY(), cubicCurve.getCtrlX1(), cubicCurve.getCtrlY1(),
                cubicCurve.getCtrlX2(), cubicCurve.getCtrlY2(), cubicCurve.getEndX(), cubicCurve.getEndY());
    }

    @Override
    public void drawOval(final float x, final float y, final float width, final float height) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        canvas.save();
        mRectF.set(x, y, x + width, y + height);
        canvas.drawOval(mRectF, paint);
        canvas.restore();
    }

    @Override
    public void drawOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        drawOval(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public void drawOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        canvas.save();
        canvas.drawOval(((AndroidA3Rect)rect).rectF, paint);
        canvas.restore();
    }

    @Override
    public void drawOval(final A3Oval oval) {
        checkArgNotNull(oval, "oval");
        drawOval(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
    }

    @Override
    public void drawRect(final float x, final float y, final float width, final float height) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        canvas.save();
        mRectF.set(x, y, x + width, y + height);
        canvas.drawRect(mRectF, paint);
        canvas.restore();
    }

    @Override
    public void drawRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        drawRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public void drawRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        canvas.save();
        canvas.drawRect(((AndroidA3Rect)rect).rectF, paint);
        canvas.restore();
    }

    @Override
    public void drawRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        canvas.save();
        mRectF.set(x, y, x + width, y + height);
        canvas.drawRoundRect(mRectF, rx, ry, paint);
        canvas.restore();
    }

    @Override
    public void drawRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        drawRoundRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), corner.getWidth(), corner.getHeight());
    }

    @Override
    public void drawRoundRect(final A3Rect rect, final A3Size corner) {
        checkArgNotNull(rect, "rect");
        checkArgNotNull(corner, "corner");
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        canvas.save();
        canvas.drawRoundRect(((AndroidA3Rect)rect).rectF, corner.getWidth(), corner.getHeight(), paint);
        canvas.restore();
    }

    @Override
    public void drawRoundRect(final A3RoundRect roundRect) {
        checkArgNotNull(roundRect, "roundRect");
        drawRoundRect(roundRect.getX(), roundRect.getY(), roundRect.getWidth(), roundRect.getHeight(), roundRect.getArcWidth(), roundRect.getArcHeight());
    }

    @Override
    public void drawText(final CharSequence text, final int start, final int end, final float x, final float y) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        canvas.save();
        canvas.drawText(text, start, end, x, y, paint);
        canvas.restore();
    }

    @Override
    public void drawText(final char[] text, final int offset, final int length, final float x, final float y) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        canvas.save();
        canvas.drawText(text, offset, length, x, y, paint);
        canvas.restore();
    }

    @Override
    public float measureText(final CharSequence text, final int start, final int end) {
        checkDisposed("Can't call measureText() on a disposed A3Graphics");
        return paint.measureText(text, start, end);
    }

    @Override
    public float measureText(final char[] text, final int offset, final int length) {
        checkDisposed("Can't call measureText() on a disposed A3Graphics");
        return paint.measureText(text, offset, length);
    }

    @Override
    public A3Font.Metrics getFontMetrics() {
        checkDisposed("Can't call getFontMetrics() on a disposed A3Graphics");
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return new A3Font.DefaultMetrics(0, fontMetrics.ascent, fontMetrics.descent, fontMetrics.leading, fontMetrics.top, fontMetrics.bottom);
    }

    @Override
    public void getFontMetrics(final A3Font.Metrics metrics) {
        checkDisposed("Can't call getFontMetrics() on a disposed A3Graphics");
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        metrics.set(0, fontMetrics.ascent, fontMetrics.descent, fontMetrics.leading, fontMetrics.top, fontMetrics.bottom);
    }

    @Override
    public A3Rect getTextBounds(final CharSequence text, final int start, final int end) {
        final A3Rect bounds = new AndroidA3Rect(new RectF());
        getTextBounds(text, start, end, bounds);
        return bounds;
    }

    @Override
    public void getTextBounds(final CharSequence text, final int start, final int end, final A3Rect bounds) {
        final int length = end - start;
        getTextBounds(A3TextUtils.getChars(text, start, length), 0, length, bounds);
    }

    @Override
    public A3Rect getTextBounds(final char[] text, final int offset, final int length) {
        final A3Rect bounds = new AndroidA3Rect(new RectF());
        getTextBounds(text, offset, length, bounds);
        return bounds;
    }

    @Override
    public void getTextBounds(final char[] text, final int offset, final int length, final A3Rect bounds) {
        checkArgNotEmpty(text, "text");
        checkArgNotNull(bounds, "bounds");
        checkDisposed("Can't call getTextBounds() on a disposed A3Graphics");
        final Rect bounds0 = new Rect();
        paint.getTextBounds(text, offset, length, bounds0);
        bounds.setBounds(bounds0.left, bounds0.top, bounds0.right, bounds0.bottom);
    }

    @Override
    public A3Rect getClipBounds() {
        if (mClipRect != null) return new AndroidA3Rect(new RectF(mClipRect));
        else if (mClipPath != null) {
            final RectF clipBounds = new RectF();
            mClipPath.computeBounds(clipBounds, true);
            return new AndroidA3Rect(clipBounds);
        }
        else return null;
    }

    @Override
    public void getClipBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        final A3Rect clipBounds = getClipBounds();
        if (clipBounds != null) clipBounds.to(bounds);
    }

    @Override
    public A3Rect getClipRect() {
        if (mClipRect != null) return new AndroidA3Rect(new RectF(mClipRect));
        else return null;
    }

    @Override
    public void getClipRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        if (mClipRect != null) ((AndroidA3Rect)rect).rectF.set(mClipRect);
    }

    @Override
    public A3Path getClipPath() {
        if (mClipPath != null) return new AndroidA3Path(new Path(mClipPath));
        else return null;
    }

    @Override
    public void getClipPath(final A3Path path) {
        checkArgNotNull(path, "path");
        if (mClipPath != null) {
            final Path path0 = ((AndroidA3Path)path).path;
            path0.reset();
            path0.addPath(mClipPath);
        }
    }

    @Override
    public A3Graphics setClipRect(final float x, final float y, final float width, final float height) {
        mClipPath = null;
        mClipRect = new RectF(x, y, x + width, y + height);
        canvas.clipRect(mClipRect);
        return this;
    }

    @Override
    public A3Graphics setClipRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return setClipRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Graphics setClipRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return setClipRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Graphics setClipPath(final A3Path path) {
        mClipRect = null;
        mClipPath = new Path(((AndroidA3Path)path).path);
        canvas.clipPath(mClipPath);
        return this;
    }

    @Override
    public A3Transform getTransform() {
        checkDisposed("Can't call getTransform() on a disposed A3Graphics");
        return new AndroidA3Transform(mTransformMatrix);
    }

    @Override
    public void getTransform(final A3Transform transform) {
        checkArgNotNull(transform, "transform");
        getTransform().to(transform);
    }

    @Override
    public A3Graphics setTransform(final A3Transform transform) {
        checkDisposed("Can't call setTransform() on a disposed A3Graphics");
        if (transform == null) canvas.setMatrix(null);
        else {
            mTransformMatrix.set(((AndroidA3Transform)transform).matrix);
            canvas.setMatrix(mTransformMatrix);
        }
        data.setTransform(transform);
        return this;
    }

    @Override
    public A3Graphics setTransform(final float[] matrixValues) {
        checkArgNotNull(matrixValues, "matrixValues");
        final float[] values = new float[AndroidA3Transform.ANDROID_MATRIX_VALUES_LENGTH];
        System.arraycopy(matrixValues, 0, values, 0, AndroidA3Transform.MATRIX_VALUES_LENGTH);
        values[6] = 0;
        values[7] = 0;
        values[8] = 1;
        final Matrix matrix = new Matrix();
        matrix.setValues(values);
        return setTransform(new AndroidA3Transform(matrix));
    }

    @Override
    public A3Graphics setTransform(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        final Matrix matrix = new Matrix();
        matrix.setValues(new float[] {sx, kx, dx, ky, sy, dy, 0, 0, 1});
        return setTransform(new AndroidA3Transform(matrix));
    }

    @Override
    public A3Graphics setTransform(A3Point scale, A3Point skew, A3Point translate) {
        return null;
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return paint.getColor();
    }

    @Override
    public A3Graphics setColor(final int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        data.setColor(color);
        paint.setColor(color);
        return this;
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return paintStyle2Style(paint.getStyle());
    }

    @Override
    public A3Graphics setStyle(final int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        data.setStyle(style);
        paint.setStyle(style2PaintStyle(style));
        return this;
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return paint.getStrokeWidth();
    }

    @Override
    public A3Graphics setStrokeWidth(final float width) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        data.setStrokeWidth(width);
        paint.setStrokeWidth(width);
        return this;
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return paintStrokeJoin2StrokeJoin(paint.getStrokeJoin());
    }

    @Override
    public A3Graphics setStrokeJoin(final int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        data.setStrokeJoin(join);
        paint.setStrokeJoin(strokeJoin2PaintStrokeJoin(join));
        return this;
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return paintStrokeCap2StrokeCap(paint.getStrokeCap());
    }

    @Override
    public A3Graphics setStrokeCap(final int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        data.setStrokeCap(cap);
        paint.setStrokeCap(strokeCap2PaintStrokeCap(cap));
        return this;
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return paint.getStrokeMiter();
    }

    @Override
    public A3Graphics setStrokeMiter(final float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        data.setStrokeMiter(miter);
        paint.setStrokeMiter(miter);
        return this;
    }

    @Override
    public A3Font getFont() {
        checkDisposed("Can't call getFont() on a disposed A3Graphics");
        if (paint.getTypeface() == null) return null;
        else {
            final AndroidA3Font font = (AndroidA3Font) data.getFont();
            if (font != null && font.getTypeface().equals(paint.getTypeface())) return font;
            else return new AndroidA3Font(paint.getTypeface());
        }
    }

    @Override
    public A3Graphics setFont(final A3Font font) {
        checkDisposed("Can't call setFont() on a disposed A3Graphics");
        data.setFont(font);
        if (font != null) paint.setTypeface(((AndroidA3Font)font).getTypeface());
        return this;
    }

    @Override
    public float getTextSize() {
        checkDisposed("Can't call getTextSize() on a disposed A3Graphics");
        return paint.getTextSize();
    }

    @Override
    public A3Graphics setTextSize(float size) {
        checkDisposed("Can't call setTextSize() on a disposed A3Graphics");
        data.setTextSize(size);
        paint.setTextSize(size);
        return this;
    }

    @Override
    public boolean isAntiAlias() {
        checkDisposed("Can't call isAntiAlias() on a disposed A3Graphics");
        return paint.isAntiAlias() && paint.isLinearText();
    }

    @Override
    public A3Graphics setAntiAlias(final boolean antiAlias) {
        checkDisposed("Can't call setAntiAlias() on a disposed A3Graphics");
        data.setAntiAlias(antiAlias);
        paint.setAntiAlias(antiAlias);
        paint.setLinearText(antiAlias);
        return this;
    }

    @Override
    public boolean isFilterImage() {
        checkDisposed("Can't call isFilterImage() on a disposed A3Graphics");
        return paint.isFilterBitmap();
    }

    @Override
    public A3Graphics setFilterImage(final boolean filterImage) {
        checkDisposed("Can't call setFilterImage() on a disposed A3Graphics");
        data.setFilterImage(filterImage);
        paint.setFilterBitmap(filterImage);
        return this;
    }

    @Override
    public boolean isSubpixelText() {
        checkDisposed("Can't call isSubpixelText() on a disposed A3Graphics");
        return paint.isSubpixelText();
    }

    @Override
    public A3Graphics setSubpixelText(final boolean subpixelText) {
        checkDisposed("Can't call setSubpixelText() on a disposed A3Graphics");
        data.setSubpixelText(subpixelText);
        paint.setSubpixelText(true);
        return this;
    }

    @Override
    public boolean isUnderlineText() {
        checkDisposed("Can't call isUnderlineText() on a disposed A3Graphics");
        return paint.isUnderlineText();
    }

    @Override
    public A3Graphics setUnderlineText(final boolean underlineText) {
        checkDisposed("Can't call setUnderlineText() on a disposed A3Graphics");
        data.setUnderlineText(underlineText);
        paint.setUnderlineText(true);
        return this;
    }

    @Override
    public boolean isStrikeThroughText() {
        checkDisposed("Can't call isStrikeThroughText() on a disposed A3Graphics");
        return paint.isStrikeThruText();
    }

    @Override
    public A3Graphics setStrikeThroughText(final boolean strikeThroughText) {
        checkDisposed("Can't call setStrikeThroughText() on a disposed A3Graphics");
        data.setStrikeThroughText(strikeThroughText);
        paint.setStrikeThruText(strikeThroughText);
        return this;
    }

    @Override
    public boolean isDither() {
        checkDisposed("Can't call isDither() on a disposed A3Graphics");
        return paint.isDither();
    }

    @Override
    public A3Graphics setDither(final boolean dither) {
        checkDisposed("Can't call setDither() on a disposed A3Graphics");
        data.setDither(dither);
        paint.setDither(dither);
        return this;
    }

    @Override
    public A3Graphics reset() {
        checkDisposed("Can't call reset() on a disposed A3Graphics");
        data.reset();
        apply();
        return this;
    }

    @Override
    public void save() {
        checkDisposed("Can't call save() on a disposed A3Graphics");
        data.to(cacheData);
    }

    @Override
    public void restore() {
        checkDisposed("Can't call restore() on a disposed A3Graphics");
        data.from(cacheData);
        apply();
    }

    @Override
    public void apply() {
        checkDisposed("Can't call apply() on a disposed A3Graphics");
        final A3Path clipPath = data.getClipPath();
        if (clipPath != null) setClipPath(clipPath);
        else {
            final A3Rect clipRect = data.getClipRect();
            if (clipRect != null) setClipRect(clipRect);
        }
        paint.setColor(data.getColor());
        paint.setStyle(style2PaintStyle(data.getStyle()));
        paint.setStrokeWidth(data.getStrokeWidth());
        paint.setStrokeJoin(strokeJoin2PaintStrokeJoin(data.getStrokeJoin()));
        paint.setStrokeCap(strokeCap2PaintStrokeCap(data.getStrokeCap()));
        paint.setStrokeMiter(data.getStrokeMiter());
        A3Font font = data.getFont();
        if (font != null) paint.setTypeface(((AndroidA3Font)font).getTypeface());
        paint.setTextSize(data.getTextSize());
        paint.setAntiAlias(data.isAntiAlias());
        paint.setLinearText(data.isAntiAlias());
        paint.setFilterBitmap(data.isFilterImage());
        paint.setSubpixelText(data.isSubpixelText());
        paint.setUnderlineText(data.isUnderlineText());
        paint.setStrikeThruText(data.isStrikeThroughText());
        paint.setDither(data.isDither());
        final A3Transform transform = data.getTransform();
        if (transform == null) canvas.setMatrix(null);
        else canvas.setMatrix(((AndroidA3Transform)transform).matrix);
    }

    @Override
    public Data getData() {
        checkDisposed("Can't call getData() on a disposed A3Graphics");
        data.setClipRect(getClipRect());
        data.setClipPath(getClipPath());
        data.setTransform(getTransform());
        data.setColor(getColor());
        data.setStyle(getStyle());
        data.setStrokeWidth(getStrokeWidth());
        data.setStrokeJoin(getStrokeJoin());
        data.setStrokeCap(getStrokeCap());
        data.setStrokeMiter(getStrokeMiter());
        data.setFont(getFont());
        data.setTextSize(getTextSize());
        data.setAntiAlias(isAntiAlias());
        data.setFilterImage(isFilterImage());
        data.setSubpixelText(isSubpixelText());
        data.setUnderlineText(isUnderlineText());
        data.setStrikeThroughText(isStrikeThroughText());
        data.setDither(isDither());
        return data;
    }

    @Override
    public A3Graphics setData(final Data data) {
        checkDisposed("Can't call setData() on a disposed A3Graphics");
        checkArgNotNull(data, "data");
        data.to(this.data);
        apply();
        return this;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        canvas = null;
    }

}

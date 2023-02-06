package io.notcute.g2d.android;

import android.graphics.*;
import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Font;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.PathIterator;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.util.AlreadyDisposedException;

import java.util.Objects;

public class AndroidGraphics implements Graphics {

    private final Canvas canvas;
    private final int width, height;

    private final Info info;
    private final Info cacheInfo;

    private volatile boolean disposed = false;

    private final Paint paint;

    public AndroidGraphics(Bitmap bitmap) {
        this(new Canvas(bitmap), bitmap.getWidth(), bitmap.getHeight());
    }

    public AndroidGraphics(Canvas canvas, int width, int height) {
        this.canvas = Objects.requireNonNull(canvas);
        this.width = width;
        this.height = height;
        info = new Info();
        cacheInfo = new Info();
        paint = new Paint();
    }

    public void applyInfo() {
        if (isDisposed()) throw new AlreadyDisposedException();
        canvas.setMatrix(Util.toAndroidMatrix(info.getTransform()));
        canvas.clipPath(Util.toAndroidPath(info.getClip().getPathIterator()));
        paint.setColor(info.getColor());
        paint.setStyle(Util.toAndroidPaintStyle(info.getStyle()));
        paint.setStrokeWidth(info.getStrokeWidth());
        paint.setStrokeJoin(Util.toAndroidPaintJoin(info.getStrokeJoin()));
        paint.setStrokeCap(Util.toAndroidPaintCap(info.getStrokeCap()));
        paint.setStrokeMiter(info.getStrokeMiter());
        Font font = info.getFont();
        if (font != null) paint.setTypeface(((AndroidFont) font).getTypeface());
        paint.setTextSize(info.getTextSize());
        paint.setAntiAlias(info.isAntiAlias());
        paint.setLinearText(info.isAntiAlias());
        paint.setFilterBitmap(info.isFilterImage());
        paint.setSubpixelText(info.isSubpixelText());
        paint.setUnderlineText(info.isUnderlineText());
        paint.setStrikeThruText(info.isStrikeThroughText());
        paint.setDither(info.isDither());
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return width;
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return height;
    }

    @Override
    public void drawColor() {
        applyInfo();
        canvas.save();
        canvas.drawColor(paint.getColor());
        canvas.restore();
    }

    @Override
    public void drawImage(Image image, AffineTransform transform) {
        applyInfo();
        canvas.save();
        canvas.drawBitmap(((AndroidImage) image).getBitmap(), Util.toAndroidMatrix(transform), paint);
        canvas.restore();
    }

    @Override
    public void drawPoint(float x, float y) {
        applyInfo();
        canvas.save();
        canvas.drawPoint(x, y, paint);
        canvas.restore();
    }

    @Override
    public void drawPathIterator(PathIterator iterator) {
        applyInfo();
        canvas.save();
        canvas.drawPath(Util.toAndroidPath(iterator), paint);
        canvas.restore();
    }

    @Override
    public void drawText(CharSequence text, int start, int end, AffineTransform transform) {
        applyInfo();
        canvas.save();
        canvas.concat(Util.toAndroidMatrix(transform));
        canvas.drawText(text, start, end, 0, 0, paint);
        canvas.restore();
    }

    @Override
    public void drawText(char[] text, int offset, int length, AffineTransform transform) {
        applyInfo();
        canvas.save();
        canvas.setMatrix(Util.toAndroidMatrix(transform));
        canvas.drawText(text, offset, length, 0, 0, paint);
        canvas.restore();
    }

    @Override
    public float measureText(CharSequence text, int start, int end) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return paint.measureText(text, start, end);
    }

    @Override
    public float measureText(char[] text, int offset, int length) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return paint.measureText(text, offset, length);
    }

    @Override
    public void getFontMetrics(Font.Metrics metrics) {
        if (isDisposed()) throw new AlreadyDisposedException();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        metrics.setMetrics(0, fontMetrics.ascent, fontMetrics.descent, fontMetrics.leading, fontMetrics.top, fontMetrics.bottom);
    }

    @Override
    public void getTextBounds(CharSequence text, int start, int end, Rectangle bounds) {
        if (isDisposed()) throw new AlreadyDisposedException();
        getTextBounds(Util.getChars(text, start, end), start, end, bounds);
    }

    @Override
    public void getTextBounds(char[] text, int offset, int length, Rectangle bounds) {
        Objects.requireNonNull(bounds);
        if (isDisposed()) throw new AlreadyDisposedException();
        Rect rect = new Rect();
        paint.getTextBounds(text, offset, length, rect);
        bounds.setRect(rect.left, rect.top, rect.width(), rect.height());
    }

    @Override
    public Info getInfo() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return info;
    }

    Info getCacheInfo() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return cacheInfo;
    }

    void setCacheInfo(Info info) {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.to(cacheInfo);
    }

    @Override
    public void save() {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.to(cacheInfo);
    }

    @Override
    public void restore() {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.from(cacheInfo);
    }

}

package com.ansdoship.a3wt.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;

import static com.ansdoship.a3wt.android.A3AndroidUtils.paintStyle2Style;
import static com.ansdoship.a3wt.android.A3AndroidUtils.style2PaintStyle;
import static com.ansdoship.a3wt.android.A3AndroidUtils.paintStrokeJoin2StrokeJoin;
import static com.ansdoship.a3wt.android.A3AndroidUtils.strokeJoin2PaintStrokeJoin;
import static com.ansdoship.a3wt.android.A3AndroidUtils.paintStrokeCap2StrokeCap;
import static com.ansdoship.a3wt.android.A3AndroidUtils.strokeCap2PaintStrokeCap;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Graphics implements A3Graphics {

    protected volatile Canvas canvas;
    protected volatile Paint paint;
    protected volatile boolean disposed = false;
    protected volatile int width;
    protected volatile int height;
    protected volatile Data data;
    protected volatile Data cacheData;
    protected volatile AndroidA3Path clip = null;

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
        paint = new Paint();
        reset();
    }

    public Canvas getCanvas() {
        return canvas;
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
    public void drawImage(final A3Image image, final int x, final int y) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        canvas.save();
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(),
                x - image.getHotSpotX(), y - image.getHotSpotY(),
                paint);
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
    public void drawArc(final float left, final float top, final float right, final float bottom,
                        final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        canvas.save();
        canvas.drawArc(new RectF(left, top, right, bottom), startAngle, sweepAngle, useCenter, paint);
        canvas.restore();
    }

    @Override
    public void drawLine(final float startX, final float startY, final float stopX, final float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        canvas.save();
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.restore();
    }

    @Override
    public void drawOval(final float left, final float top, final float right, final float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        canvas.save();
        canvas.drawOval(new RectF(left, top, right, bottom), paint);
        canvas.restore();
    }

    @Override
    public void drawRect(final float left, final float top, final float right, final float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        canvas.save();
        canvas.drawRect(new RectF(left, top, right, bottom), paint);
        canvas.restore();
    }

    @Override
    public void drawRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        canvas.save();
        canvas.drawRoundRect(new RectF(left, top, right, bottom), rx, ry, paint);
        canvas.restore();
    }

    @Override
    public void drawText(final CharSequence text, final float x, final float y) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        canvas.save();
        canvas.drawText(text, 0, text.length(), x, y, paint);
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
    public A3Font.Metrics getTextLayout(CharSequence text) {
        return null;
    }

    @Override
    public A3Font.Metrics getTextLayout(char[] text, int offset, int length) {
        return null;
    }

    @Override
    public A3Path getClip() {
        checkDisposed("Can't call getClip() on a disposed A3Graphics");
        return clip;
    }

    @Override
    public void setClip(final A3Path clip) {
        checkArgNotNull(clip, "clip");
        checkDisposed("Can't call setClip() on a disposed A3Graphics");
        this.clip = (AndroidA3Path) clip;
        canvas.clipPath(this.clip.path);
    }

    @Override
    public void setClip(final float left, final float top, final float right, final float bottom) {
        checkDisposed("Can't call setClip() on a disposed A3Graphics");
        if (clip == null) clip = new AndroidA3Path(new Path());
        else clip.reset();
        clip.addRect(left, top, right, bottom);
        canvas.clipRect(left, top, right, bottom);
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return paint.getColor();
    }

    @Override
    public void setColor(final int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        data.setColor(color);
        paint.setColor(color);
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return paintStyle2Style(paint.getStyle());
    }

    @Override
    public void setStyle(final int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        data.setStyle(style);
        paint.setStyle(style2PaintStyle(style));
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return paint.getStrokeWidth();
    }

    @Override
    public void setStrokeWidth(final float width) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        data.setStrokeWidth(width);
        paint.setStrokeWidth(width);
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return paintStrokeJoin2StrokeJoin(paint.getStrokeJoin());
    }

    @Override
    public void setStrokeJoin(final int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        data.setStrokeJoin(join);
        paint.setStrokeJoin(strokeJoin2PaintStrokeJoin(join));
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return paintStrokeCap2StrokeCap(paint.getStrokeCap());
    }

    @Override
    public void setStrokeCap(final int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        data.setStrokeCap(cap);
        paint.setStrokeCap(strokeCap2PaintStrokeCap(cap));
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return paint.getStrokeMiter();
    }

    @Override
    public void setStrokeMiter(final float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        data.setStrokeMiter(miter);
        paint.setStrokeMiter(miter);
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
    public void setFont(final A3Font font) {
        checkDisposed("Can't call setFont() on a disposed A3Graphics");
        data.setFont(font);
        if (font != null) paint.setTypeface(((AndroidA3Font)font).getTypeface());
    }

    @Override
    public float getTextSize() {
        checkDisposed("Can't call getTextSize() on a disposed A3Graphics");
        return paint.getTextSize();
    }

    @Override
    public void setTextSize(float size) {
        checkDisposed("Can't call setTextSize() on a disposed A3Graphics");
        data.setTextSize(size);
        paint.setTextSize(size);
    }

    @Override
    public boolean isAntiAlias() {
        checkDisposed("Can't call isAntiAlias() on a disposed A3Graphics");
        return paint.isAntiAlias() && paint.isLinearText();
    }

    @Override
    public void setAntiAlias(final boolean antiAlias) {
        checkDisposed("Can't call setAntiAlias() on a disposed A3Graphics");
        data.setAntiAlias(antiAlias);
        paint.setAntiAlias(antiAlias);
        paint.setLinearText(antiAlias);
    }

    @Override
    public boolean isFilterImage() {
        checkDisposed("Can't call isFilterImage() on a disposed A3Graphics");
        return paint.isFilterBitmap();
    }

    @Override
    public void setFilterImage(final boolean filterImage) {
        checkDisposed("Can't call setFilterImage() on a disposed A3Graphics");
        data.setFilterImage(filterImage);
        paint.setFilterBitmap(filterImage);
    }

    @Override
    public boolean isSubpixelText() {
        checkDisposed("Can't call isSubpixelText() on a disposed A3Graphics");
        return paint.isSubpixelText();
    }

    @Override
    public void setSubpixelText(final boolean subpixelText) {
        checkDisposed("Can't call setSubpixelText() on a disposed A3Graphics");
        data.setSubpixelText(subpixelText);
        paint.setSubpixelText(true);
    }

    @Override
    public boolean isUnderlineText() {
        checkDisposed("Can't call isUnderlineText() on a disposed A3Graphics");
        return paint.isUnderlineText();
    }

    @Override
    public void setUnderlineText(final boolean underlineText) {
        checkDisposed("Can't call setUnderlineText() on a disposed A3Graphics");
        data.setUnderlineText(underlineText);
        paint.setUnderlineText(true);
    }

    @Override
    public boolean isStrikeThroughText() {
        checkDisposed("Can't call isStrikeThroughText() on a disposed A3Graphics");
        return paint.isStrikeThruText();
    }

    @Override
    public void setStrikeThroughText(final boolean strikeThroughText) {
        checkDisposed("Can't call setStrikeThroughText() on a disposed A3Graphics");
        data.setStrikeThroughText(strikeThroughText);
        paint.setStrikeThruText(strikeThroughText);
    }

    @Override
    public boolean isDither() {
        checkDisposed("Can't call isDither() on a disposed A3Graphics");
        return paint.isDither();
    }

    @Override
    public void setDither(final boolean dither) {
        checkDisposed("Can't call setDither() on a disposed A3Graphics");
        data.setDither(dither);
        paint.setDither(dither);
    }

    @Override
    public void reset() {
        checkDisposed("Can't call reset() on a disposed A3Graphics");
        if (data == null) data = new DefaultData();
        else data.reset();
        apply();
    }

    @Override
    public void save() {
        checkDisposed("Can't call save() on a disposed A3Graphics");
        if (!data.equals(cacheData)) cacheData = data.copy();
    }

    @Override
    public void restore() {
        checkDisposed("Can't call restore() on a disposed A3Graphics");
        if (cacheData == null) return;
        data = cacheData;
        cacheData = null;
        apply();
    }

    @Override
    public void apply() {
        checkDisposed("Can't call apply() on a disposed A3Graphics");
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
    }

    @Override
    public Data getData() {
        checkDisposed("Can't call getData() on a disposed A3Graphics");
        if (data == null) data = new DefaultData();
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
    public void setData(final Data data) {
        checkDisposed("Can't call setData() on a disposed A3Graphics");
        this.data = data;
        apply();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        data = null;
        cacheData = null;
        canvas = null;
        paint = null;
    }

}

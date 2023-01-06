package com.ansdoship.a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.graphics.A3Size;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Rect implements A3Rect {
    
    protected final RectF rectF;
    
    public AndroidA3Rect(final RectF rectF) {
        checkArgNotNull(rectF, "rectF");
        this.rectF = rectF;
    }

    public RectF getRectF() {
        return rectF;
    }

    @Override
    public A3Rect getBounds() {
        return new AndroidA3Rect(new RectF(rectF));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        bounds.set(rectF.left, rectF.top, rectF.width(), rectF.height());
    }

    @Override
    public float getLeft() {
        return rectF.left;
    }

    @Override
    public float getTop() {
        return rectF.top;
    }

    @Override
    public float getRight() {
        return rectF.right;
    }

    @Override
    public float getBottom() {
        return rectF.bottom;
    }

    @Override
    public float getX() {
        return rectF.left;
    }

    @Override
    public float getY() {
        return rectF.top;
    }

    @Override
    public A3Point getPos() {
        return new AndroidA3Point(new PointF(rectF.left, rectF.top));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(rectF.left, rectF.top);
    }

    @Override
    public float getWidth() {
        return rectF.right - rectF.left;
    }

    @Override
    public float getHeight() {
        return rectF.bottom - rectF.top;
    }

    @Override
    public A3Size getSize() {
        return new AndroidA3Size(getWidth(), getHeight());
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(getWidth(), getHeight());
    }

    @Override
    public A3Rect setLeft(final float left) {
        rectF.left = left;
        return this;
    }

    @Override
    public A3Rect setTop(final float top) {
        rectF.top = top;
        return this;
    }

    @Override
    public A3Rect setRight(final float right) {
        rectF.right = right;
        return this;
    }

    @Override
    public A3Rect setBottom(final float bottom) {
        rectF.bottom = bottom;
        return this;
    }

    @Override
    public A3Rect setBounds(final float left, final float top, final float right, final float bottom) {
        rectF.set(left, top, right, bottom);
        return this;
    }

    @Override
    public A3Rect setX(final float x) {
        rectF.right += x - rectF.left;
        rectF.left = x;
        return this;
    }

    @Override
    public A3Rect setY(final float y) {
        rectF.bottom += y - rectF.bottom;
        rectF.top = y;
        return this;
    }

    @Override
    public A3Rect setWidth(final float width) {
        rectF.right += rectF.width() - width;
        return this;
    }

    @Override
    public A3Rect setHeight(final float height) {
        rectF.bottom += rectF.height() - height;
        return this;
    }

    @Override
    public A3Rect setPos(final float x, final float y) {
        rectF.offsetTo(x, y);
        return this;
    }

    @Override
    public A3Rect setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        rectF.offsetTo(pos.getX(), pos.getY());
        return this;
    }

    @Override
    public A3Rect setSize(final float width, final float height) {
        rectF.right += rectF.width() - width;
        rectF.bottom += rectF.height() - height;
        return this;
    }

    @Override
    public A3Rect setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        rectF.right += rectF.width() - size.getWidth();
        rectF.bottom += rectF.height() - size.getHeight();
        return this;
    }

    @Override
    public A3Rect set(final float x, final float y, final float width, final float height) {
        rectF.set(x, y, x + width, y + height);
        return this;
    }

    @Override
    public A3Rect set(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return set(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Rect copy() {
        return new AndroidA3Rect(new RectF(rectF));
    }

    @Override
    public void to(final A3Rect dst) {
        checkArgNotNull(dst, "dst");
        ((AndroidA3Rect)dst).rectF.set(rectF);
    }

    @Override
    public void from(final A3Rect src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect reset() {
        rectF.set(0, 0, 0, 0);
        return this;
    }

    @Override
    public boolean contains(final float x, final float y) {
        return rectF.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return rectF.contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return rectF.contains(x, y, x + width, y + height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return rectF.contains(((AndroidA3Rect)rect).rectF);
    }

}

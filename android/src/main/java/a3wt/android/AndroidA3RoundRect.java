package a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3RoundRect;
import a3wt.graphics.A3Size;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeBounds;
import static a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AndroidA3RoundRect implements A3RoundRect {
    
    protected float x, y, width, height, rx, ry;

    public AndroidA3RoundRect() {
        reset();
    }

    public AndroidA3RoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public A3Rect getBounds() {
        return new AndroidA3Rect(new RectF(x, y, x + width, y + height));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        bounds.set(x, y, width, height);
    }

    @Override
    public float getLeft() {
        return x;
    }

    @Override
    public float getTop() {
        return y;
    }

    @Override
    public float getRight() {
        return x + width;
    }

    @Override
    public float getBottom() {
        return y + height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public A3Point getPos() {
        return new AndroidA3Point(new PointF(x, y));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(x, y);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public A3Size getSize() {
        return new AndroidA3Size(width, height);
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(width, height);
    }

    @Override
    public float getArcWidth() {
        return rx;
    }

    @Override
    public float getArcHeight() {
        return ry;
    }

    @Override
    public A3Size getCorner() {
        return new AndroidA3Size(rx, ry);
    }

    @Override
    public void getCorner(final A3Size corner) {
        checkArgNotNull(corner, "corner");
        corner.set(rx, ry);
    }

    @Override
    public A3RoundRect setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        width += x - left;
        x = left;
        return this;
    }

    @Override
    public A3RoundRect setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        height += y - top;
        y = top;
        return this;
    }

    @Override
    public A3RoundRect setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        width = right - x;
        return this;
    }

    @Override
    public A3RoundRect setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        height = bottom - y;
        return this;
    }

    @Override
    public A3RoundRect setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        x = left;
        y = top;
        width = right - left;
        height = bottom - top;
        return this;
    }

    @Override
    public A3RoundRect setX(final float x) {
        this.x = x;
        return this;
    }

    @Override
    public A3RoundRect setY(final float y) {
        this.y = y;
        return this;
    }

    @Override
    public A3RoundRect setWidth(final float width) {
        this.width = width;
        return this;
    }

    @Override
    public A3RoundRect setHeight(final float height) {
        this.height = height;
        return this;
    }

    @Override
    public A3RoundRect setPos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public A3RoundRect setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        x = pos.getX();
        y = pos.getY();
        return this;
    }

    @Override
    public A3RoundRect setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3RoundRect setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        width = size.getWidth();
        height = size.getHeight();
        return this;
    }

    @Override
    public A3RoundRect setRect(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3RoundRect setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        x = pos.getX();
        y = pos.getY();
        width = size.getWidth();
        height = size.getHeight();
        return this;
    }

    @Override
    public A3RoundRect setArcWidth(final float rx) {
        this.rx = rx;
        return this;
    }

    @Override
    public A3RoundRect setArcHeight(final float ry) {
        this.ry = ry;
        return this;
    }

    @Override
    public A3RoundRect setCorner(final A3Size corner) {
        checkArgNotNull(corner, "corner");
        rx = corner.getWidth();
        ry = corner.getHeight();
        return this;
    }

    @Override
    public A3RoundRect set(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        return this;
    }

    @Override
    public A3RoundRect set(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        x = pos.getX();
        y = pos.getY();
        width = size.getWidth();
        height = size.getHeight();
        rx = corner.getWidth();
        ry = corner.getHeight();
        return this;
    }

    @Override
    public A3RoundRect set(final A3Rect rect, final A3Size corner) {
        checkArgNotNull(rect, "rect");
        checkArgNotNull(corner, "corner");
        x = rect.getX();
        y = rect.getY();
        width = rect.getWidth();
        height = rect.getHeight();
        rx = corner.getWidth();
        ry = corner.getHeight();
        return this;
    }

    @Override
    public boolean contains(float x, float y) {
        if (width <= 0.0f || height <= 0.0f) {
            return false;
        }
        float rrx0 = getX();
        float rry0 = getY();
        final float rrx1 = rrx0 + getWidth();
        final float rry1 = rry0 + getHeight();
        // Check for trivial rejection - point is outside bounding rectangle
        if (x < rrx0 || y < rry0 || x >= rrx1 || y >= rry1) {
            return false;
        }
        final float aw = Math.min(getWidth(), Math.abs(getArcWidth())) / 2.0f;
        final float ah = Math.min(getHeight(), Math.abs(getArcHeight())) / 2.0f;
        // Check which corner point is in and do circular containment
        // test - otherwise simple acceptance
        if (x >= (rrx0 += aw) && x < (rrx0 = rrx1 - aw)) {
            return true;
        }
        if (y >= (rry0 += ah) && y < (rry0 = rry1 - ah)) {
            return true;
        }
        x = (x - rrx0) / aw;
        y = (y - rry0) / ah;
        return (x * x + y * y <= 1.0f);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        if (this.width <= 0.0f || this.height <= 0.0f || width <= 0.0f || height <= 0.0f) {
            return false;
        }
        return (contains(x, y) &&
                contains(x + width, y) &&
                contains(x, y + height) &&
                contains(x + width, y + height));
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3RoundRect copy() {
        return new AndroidA3RoundRect(x, y, width, height, rx, ry);
    }

    @Override
    public void to(final A3RoundRect dst) {
        checkArgNotNull(dst, "dst");
        dst.set(x, y, width, height, rx, ry);
    }

    @Override
    public void from(final A3RoundRect src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3RoundRect reset() {
        x = y = width = height = rx = ry = 0;
        return this;
    }

}

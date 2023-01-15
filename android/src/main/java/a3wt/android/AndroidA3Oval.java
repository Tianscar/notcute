package a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import a3wt.graphics.A3Oval;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Size;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeBounds;
import static a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AndroidA3Oval implements A3Oval {

    protected float x, y, width, height;

    public AndroidA3Oval() {
        reset();
    }

    public AndroidA3Oval(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    public float getCenterX() {
        return x + width / 2;
    }

    @Override
    public float getCenterY() {
        return y + height / 2;
    }

    @Override
    public A3Point getCenter() {
        return new AndroidA3Point(new PointF(getCenterX(), getCenterY()));
    }

    @Override
    public void getCenter(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(getCenterX(), getCenterY());
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
    public A3Oval setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        width += x - left;
        x = left;
        return this;
    }

    @Override
    public A3Oval setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        height += y - top;
        y = top;
        return this;
    }

    @Override
    public A3Oval setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        width = right - x;
        return this;
    }

    @Override
    public A3Oval setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        height = bottom - y;
        return this;
    }

    @Override
    public A3Oval setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        x = left;
        y = top;
        width = right - left;
        height = bottom - top;
        return this;
    }

    @Override
    public A3Oval setCenterX(final float centerX) {
        x = centerX - width / 2;
        return this;
    }

    @Override
    public A3Oval setCenterY(final float centerY) {
        y = centerY - height / 2;
        return this;
    }

    @Override
    public A3Oval setCenter(final A3Point center) {
        checkArgNotNull(center, "center");
        x = center.getX() - width / 2;
        y = center.getY() - height / 2;
        return this;
    }

    @Override
    public A3Oval setX(final float x) {
        this.x = x;
        return this;
    }

    @Override
    public A3Oval setY(final float y) {
        this.y = y;
        return this;
    }

    @Override
    public A3Oval setWidth(final float width) {
        this.width = width;
        return this;
    }

    @Override
    public A3Oval setHeight(final float height) {
        this.height = height;
        return this;
    }

    @Override
    public A3Oval setPos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public A3Oval setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        x = pos.getX();
        y = pos.getY();
        return this;
    }

    @Override
    public A3Oval setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Oval setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        width = size.getWidth();
        height = size.getHeight();
        return this;
    }

    @Override
    public A3Oval setRect(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Oval setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        x = pos.getX();
        y = pos.getY();
        width = size.getWidth();
        height = size.getHeight();
        return this;
    }

    @Override
    public boolean contains(final float x, final float y) {
        if (isEmpty()) return false;
        final float a = (x - this.x) / width - 0.5f;
        final float b = (y - this.y) / height - 0.5f;
        return a * a + b * b < 0.25f;
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        if (isEmpty() || width <= 0.0f || height <= 0.0f) return false;
        final float x2 = x + width;
        final float y2 = y + height;
        return contains(x, y) && contains(x2, y) && contains(x2, y2) && contains(x, y2);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public boolean intersects(final float x, final float y, final float width, final float height) {
        if (isEmpty() || width <= 0.0f || height <= 0.0f) return false;
        final float cx = this.x + this.width / 2.0f;
        final float cy = this.y + this.height / 2.0f;
        final float x2 = x + width;
        final float y2 = y + height;
        final float nx = cx < x ? x : Math.min(cx, x2);
        final float ny = cy < y ? y : Math.min(cy, y2);
        return contains(nx, ny);
    }

    @Override
    public boolean intersects(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Oval copy() {
        return new AndroidA3Oval(x, y, width, height);
    }

    @Override
    public void to(final A3Oval dst) {
        checkArgNotNull(dst, "dst");
        dst.set(x, y, width, height);
    }

    @Override
    public void from(final A3Oval src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Oval reset() {
        x = y = width = height = 0;
        return this;
    }
    
}

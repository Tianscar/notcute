package a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import a3wt.graphics.A3Arc;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Size;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeBounds;
import static a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AndroidA3Arc implements A3Arc {

    protected float x, y, width, height, startAngle, sweepAngle;
    protected boolean useCenter;

    public AndroidA3Arc() {
        reset();
    }

    public AndroidA3Arc(final float x, final float y, final float width, final float height,
                        final float startAngle, final float sweepAngle, final boolean useCenter) {
        set(x, y, width, height, startAngle, sweepAngle, useCenter);
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
    public A3Rect getRect() {
        return new AndroidA3Rect(new RectF(x, y, x + width, y + height));
    }

    @Override
    public void getRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        rect.set(x, y, width, height);
    }

    @Override
    public float getStartAngle() {
        return startAngle;
    }

    @Override
    public float getSweepAngle() {
        return sweepAngle;
    }

    @Override
    public boolean isUseCenter() {
        return useCenter;
    }

    @Override
    public A3Arc setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        width += x - left;
        x = left;
        return this;
    }

    @Override
    public A3Arc setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        height += y - top;
        y = top;
        return this;
    }

    @Override
    public A3Arc setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        width = right - x;
        return this;
    }

    @Override
    public A3Arc setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        height = bottom - y;
        return this;
    }

    @Override
    public A3Arc setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        x = left;
        y = top;
        width = right - left;
        height = bottom - top;
        return this;
    }

    @Override
    public A3Arc setX(final float x) {
        this.x = x;
        return this;
    }

    @Override
    public A3Arc setY(final float y) {
        this.y = y;
        return this;
    }

    @Override
    public A3Arc setWidth(final float width) {
        this.width = width;
        return this;
    }

    @Override
    public A3Arc setHeight(final float height) {
        this.height = height;
        return this;
    }

    @Override
    public A3Arc setPos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public A3Arc setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return setPos(pos.getX(), pos.getY());
    }

    @Override
    public A3Arc setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Arc setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        return setSize(size.getWidth(), size.getHeight());
    }

    @Override
    public A3Arc setRect(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Arc setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return setRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Arc setRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return setRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Arc setStartAngle(final float startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    @Override
    public A3Arc setSweepAngle(final float sweepAngle) {
        this.sweepAngle = sweepAngle;
        return this;
    }

    @Override
    public A3Arc setUseCenter(final boolean useCenter) {
        this.useCenter = useCenter;
        return this;
    }

    @Override
    public A3Arc set(final float x, final float y, final float width, final float height,
                     final float startAngle, final float sweepAngle, final boolean useCenter) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        this.useCenter = useCenter;
        return this;
    }

    @Override
    public A3Arc set(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        return set(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), startAngle, sweepAngle, useCenter);
    }

    @Override
    public A3Arc set(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        return set(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), startAngle, sweepAngle, useCenter);
    }

    @Override
    public A3Rect getBounds() {
        final A3Rect bounds = new AndroidA3Rect(new RectF());
        getBounds(bounds);
        return bounds;
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Arc copy() {
        return new AndroidA3Arc(x, y, width, height, startAngle, sweepAngle, useCenter);
    }

    @Override
    public void to(final A3Arc dst) {
        checkArgNotNull(dst, "dst");
        dst.set(x, y, width, height, startAngle, sweepAngle, useCenter);
    }

    @Override
    public void from(final A3Arc src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Arc reset() {
        x = y = width = height = startAngle = sweepAngle = 0;
        useCenter = false;
        return this;
    }

}

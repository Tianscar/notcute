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
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        if (width <= 0.0f || height <= 0.0f) {
            bounds.set(x, y, width, height);
            return;
        }
        double x1, y1, x2, y2;
        if (useCenter) {
            x1 = y1 = x2 = y2 = 0.0;
        } else {
            x1 = y1 = 1.0;
            x2 = y2 = -1.0;
        }
        double angle = 0.0;
        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                // 0-3 are the four quadrants
                angle += 90.0;
                if (!containsAngle(angle)) {
                    continue;
                }
            } else if (i == 4) {
                // 4 is start angle
                angle = getStartAngle();
            } else {
                // 5 is end angle
                angle += getSweepAngle();
            }
            double rads = Math.toRadians(-angle);
            double xe = Math.cos(rads);
            double ye = Math.sin(rads);
            x1 = Math.min(x1, xe);
            y1 = Math.min(y1, ye);
            x2 = Math.max(x2, xe);
            y2 = Math.max(y2, ye);
        }
        double w = getWidth();
        double h = getHeight();
        x2 = (x2 - x1) * 0.5 * w;
        y2 = (y2 - y1) * 0.5 * h;
        x1 = getX() + (x1 * 0.5 + 0.5) * w;
        y1 = getY() + (y1 * 0.5 + 0.5) * h;
        bounds.set((float) x1, (float) y1, (float) x2, (float) y2);
    }

    @Override
    public boolean contains(final float x, final float y) {
        // Normalize the coordinates compared to the ellipse
        // having a center at 0,0 and a radius of 0.5.
        final double ellw = getWidth();
        if (ellw <= 0.0) {
            return false;
        }
        final double normx = (x - getX()) / ellw - 0.5;
        final double ellh = getHeight();
        if (ellh <= 0.0) {
            return false;
        }
        final double normy = (y - getY()) / ellh - 0.5;
        final double distSq = (normx * normx + normy * normy);
        if (distSq >= 0.25) {
            return false;
        }
        final double angExt = Math.abs(getSweepAngle());
        if (angExt >= 360.0) {
            return true;
        }
        final boolean inarc = containsAngle(-Math.toDegrees(Math.atan2(normy,
                normx)));
        if (useCenter) {
            return inarc;
        }
        if (inarc) {
            if (angExt >= 180.0) {
                return true;
            }
            // point must be outside the "pie triangle"
        } else {
            if (angExt <= 180.0) {
                return false;
            }
            // point must be inside the "pie triangle"
        }
        // The point is inside the pie triangle iff it is on the same
        // side of the line connecting the ends of the arc as the center.
        double angle = Math.toRadians(-getStartAngle());
        final double x1 = Math.cos(angle);
        final double y1 = Math.sin(angle);
        angle += Math.toRadians(-getSweepAngle());
        final double x2 = Math.cos(angle);
        final double y2 = Math.sin(angle);
        final boolean inside = (A3AndroidUtils.relativeCCW(x1, y1, x2, y2, 2*normx, 2*normy) *
                A3AndroidUtils.relativeCCW(x1, y1, x2, y2, 0, 0) >= 0);
        return inarc != inside;
    }

    /**
     * Determines whether or not the specified angle is within the
     * angular extents of the arc.
     *
     * @param angle The angle to test.
     *
     * @return {@code true} if the arc contains the angle,
     * {@code false} if the arc doesn't contain the angle.
     * @since 1.2
     */
    public boolean containsAngle(double angle) {
        double angExt = getSweepAngle();
        boolean backwards = (angExt < 0.0);
        if (backwards) {
            angExt = -angExt;
        }
        if (angExt >= 360.0) {
            return true;
        }
        angle = A3AndroidUtils.normalizeDegrees(angle) - A3AndroidUtils.normalizeDegrees(getStartAngle());
        if (backwards) {
            angle = -angle;
        }
        if (angle < 0.0) {
            angle += 360.0;
        }

        return (angle >= 0.0) && (angle < angExt);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        if (!(contains(x, y) &&
                contains(x + width, y) &&
                contains(x, y + height) &&
                contains(x + width, y + height))) {
            return false;
        }
        // If the shape is convex then we have done all the testing
        // we need.  Only PIE arcs can be concave and then only if
        // the angular extents are greater than 180 degrees.
        if (!useCenter || Math.abs(getSweepAngle()) <= 180.0) {
            return true;
        }
        // For a PIE shape we have an additional test for the case where
        // the angular extents are greater than 180 degrees and all four
        // rectangular corners are inside the shape but one of the
        // rectangle edges spans across the "missing wedge" of the arc.
        // We can test for this case by checking if the rectangle intersects
        // either of the pie angle segments.
        final RectF origrect = new RectF(x, y, x + width, y + height);
        double halfW = getWidth() / 2.0;
        double halfH = getHeight() / 2.0;
        double xc = getX() + halfW;
        double yc = getY() + halfH;
        double angle = Math.toRadians(-getStartAngle());
        double xe = xc + halfW * Math.cos(angle);
        double ye = yc + halfH * Math.sin(angle);
        if (A3AndroidUtils.intersectsLine(origrect, xc, yc, xe, ye)) {
            return false;
        }
        angle += Math.toRadians(-getSweepAngle());
        xe = xc + halfW * Math.cos(angle);
        ye = yc + halfH * Math.sin(angle);
        return !A3AndroidUtils.intersectsLine(origrect, xc, yc, xe, ye);
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

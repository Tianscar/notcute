package a3wt.awt;

import a3wt.graphics.A3Oval;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Size;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeBounds;
import static a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AWTA3Oval implements A3Oval {

    protected final Ellipse2D.Float ellipse2D;

    public AWTA3Oval(final Ellipse2D.Float ellipse2D) {
        checkArgNotNull(ellipse2D, "ellipse2D");
        this.ellipse2D = ellipse2D;
    }

    public Ellipse2D.Float getEllipse2D() {
        return ellipse2D;
    }

    @Override
    public float getLeft() {
        return ellipse2D.x;
    }

    @Override
    public float getTop() {
        return ellipse2D.y;
    }

    @Override
    public float getRight() {
        return ellipse2D.x + ellipse2D.width;
    }

    @Override
    public float getBottom() {
        return ellipse2D.y + ellipse2D.height;
    }

    @Override
    public float getX() {
        return ellipse2D.x;
    }

    @Override
    public float getY() {
        return ellipse2D.y;
    }

    @Override
    public A3Point getPos() {
        return new AWTA3Point(new Point2D.Float(ellipse2D.x, ellipse2D.y));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(ellipse2D.x, ellipse2D.y);
    }

    @Override
    public float getWidth() {
        return ellipse2D.width;
    }

    @Override
    public float getHeight() {
        return ellipse2D.height;
    }

    @Override
    public A3Size getSize() {
        return new AWTA3Size(new Dimension2D.Float(ellipse2D.width, ellipse2D.height));
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(ellipse2D.width, ellipse2D.height);
    }

    @Override
    public A3Oval setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        ellipse2D.width += ellipse2D.x - left;
        ellipse2D.x = left;
        return this;
    }

    @Override
    public A3Oval setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        ellipse2D.height += ellipse2D.y - top;
        ellipse2D.y = top;
        return this;
    }

    @Override
    public A3Oval setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        ellipse2D.width = right - ellipse2D.x;
        return this;
    }

    @Override
    public A3Oval setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        ellipse2D.height = bottom - ellipse2D.y;
        return this;
    }

    @Override
    public A3Oval setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        ellipse2D.x = left;
        ellipse2D.y = top;
        ellipse2D.width = right - left;
        ellipse2D.height = bottom - top;
        return this;
    }

    @Override
    public A3Oval setX(final float x) {
        ellipse2D.x = x;
        return this;
    }

    @Override
    public A3Oval setY(final float y) {
        ellipse2D.y = y;
        return this;
    }

    @Override
    public A3Oval setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        ellipse2D.x = pos.getX();
        ellipse2D.y = pos.getY();
        return this;
    }

    @Override
    public A3Oval setWidth(final float width) {
        ellipse2D.width = width;
        return this;
    }

    @Override
    public A3Oval setHeight(final float height) {
        ellipse2D.height = height;
        return this;
    }

    @Override
    public A3Oval setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        ellipse2D.width = size.getWidth();
        ellipse2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Oval setPos(final float x, final float y) {
        ellipse2D.x = x;
        ellipse2D.y = y;
        return this;
    }

    @Override
    public A3Oval setSize(final float width, final float height) {
        ellipse2D.width = width;
        ellipse2D.height = height;
        return this;
    }

    @Override
    public A3Oval set(final float x, final float y, final float width, final float height) {
        ellipse2D.setFrame(x, y, width, height);
        return this;
    }

    @Override
    public A3Oval set(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        ellipse2D.x = pos.getX();
        ellipse2D.y = pos.getY();
        ellipse2D.width = size.getWidth();
        ellipse2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Oval copy() {
        return new AWTA3Oval((Ellipse2D.Float) ellipse2D.clone());
    }

    @Override
    public void to(final A3Oval dst) {
        checkArgNotNull(dst, "dst");
    }

    @Override
    public void from(final A3Oval src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(A3AWTUtils.floatRectangle2D(ellipse2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(ellipse2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return ellipse2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return ellipse2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return ellipse2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        return ellipse2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public A3Oval reset() {
        ellipse2D.x = ellipse2D.y = ellipse2D.width = ellipse2D.height = 0;
        return this;
    }

}

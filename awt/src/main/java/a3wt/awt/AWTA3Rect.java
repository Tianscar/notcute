package a3wt.awt;

import a3wt.graphics.A3Line;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Size;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static a3wt.util.A3Preconditions.checkArgRangeTopBottom;
import static a3wt.util.A3Preconditions.checkArgRangeBounds;

public class AWTA3Rect implements A3Rect {

    protected Rectangle2D.Float rectangle2D;

    public AWTA3Rect(final Rectangle2D.Float rectangle2D) {
        checkArgNotNull(rectangle2D, "rectangle2D");
        this.rectangle2D = rectangle2D;
    }

    @Override
    public float getLeft() {
        return rectangle2D.x;
    }

    @Override
    public float getTop() {
        return rectangle2D.y;
    }

    @Override
    public float getRight() {
        return rectangle2D.x + rectangle2D.width;
    }

    @Override
    public float getBottom() {
        return rectangle2D.y + rectangle2D.height;
    }

    @Override
    public float getCenterX() {
        return rectangle2D.x + rectangle2D.width / 2;
    }

    @Override
    public float getCenterY() {
        return rectangle2D.y + rectangle2D.height / 2;
    }

    @Override
    public A3Point getCenter() {
        return new AWTA3Point(new Point2D.Float(getCenterX(), getCenterY()));
    }

    @Override
    public void getCenter(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(getCenterX(), getCenterY());
    }

    @Override
    public float getX() {
        return rectangle2D.x;
    }

    @Override
    public float getY() {
        return rectangle2D.y;
    }

    @Override
    public A3Point getPos() {
        return new AWTA3Point(new Point2D.Float(rectangle2D.x, rectangle2D.y));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(rectangle2D.x, rectangle2D.y);
    }

    @Override
    public float getWidth() {
        return rectangle2D.width;
    }

    @Override
    public float getHeight() {
        return rectangle2D.height;
    }

    @Override
    public A3Size getSize() {
        return new AWTA3Size(new Dimension2D.Float(rectangle2D.width, rectangle2D.height));
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(rectangle2D.width, rectangle2D.height);
    }

    @Override
    public A3Rect setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        rectangle2D.width += rectangle2D.x - left;
        rectangle2D.x = left;
        return this;
    }

    @Override
    public A3Rect setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        rectangle2D.height += rectangle2D.y - top;
        rectangle2D.y = top;
        return this;
    }

    @Override
    public A3Rect setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        rectangle2D.width = right - rectangle2D.x;
        return this;
    }

    @Override
    public A3Rect setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        rectangle2D.height = bottom - rectangle2D.y;
        return this;
    }

    @Override
    public A3Rect setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        rectangle2D.x = left;
        rectangle2D.y = top;
        rectangle2D.width = right - left;
        rectangle2D.height = bottom - top;
        return this;
    }

    @Override
    public A3Rect setCenterX(final float centerX) {
        rectangle2D.x = centerX - rectangle2D.width / 2;
        return this;
    }

    @Override
    public A3Rect setCenterY(final float centerY) {
        rectangle2D.y = centerY - rectangle2D.height / 2;
        return this;
    }

    @Override
    public A3Rect setCenter(final A3Point center) {
        checkArgNotNull(center, "center");
        rectangle2D.x = center.getX() - rectangle2D.width / 2;
        rectangle2D.y = center.getY() - rectangle2D.height / 2;
        return this;
    }

    @Override
    public A3Rect setX(final float x) {
        rectangle2D.x = x;
        return this;
    }

    @Override
    public A3Rect setY(final float y) {
        rectangle2D.y = y;
        return this;
    }

    @Override
    public A3Rect setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        rectangle2D.x = pos.getX();
        rectangle2D.y = pos.getY();
        return this;
    }

    @Override
    public A3Rect setWidth(final float width) {
        rectangle2D.width = width;
        return this;
    }

    @Override
    public A3Rect setHeight(final float height) {
        rectangle2D.height = height;
        return this;
    }

    @Override
    public A3Rect setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        rectangle2D.width = size.getWidth();
        rectangle2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Rect setPos(final float x, final float y) {
        rectangle2D.x = x;
        rectangle2D.y = y;
        return this;
    }

    @Override
    public A3Rect setSize(final float width, final float height) {
        rectangle2D.width = width;
        rectangle2D.height = height;
        return this;
    }

    @Override
    public A3Rect setRect(final float x, final float y, final float width, final float height) {
        rectangle2D.setRect(x, y, width, height);
        return this;
    }

    @Override
    public A3Rect setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        rectangle2D.x = pos.getX();
        rectangle2D.y = pos.getY();
        rectangle2D.width = size.getWidth();
        rectangle2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Rect copy() {
        return new AWTA3Rect((Rectangle2D.Float) rectangle2D.clone());
    }

    @Override
    public void to(final A3Rect dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Rect)dst).rectangle2D.setRect(rectangle2D);
    }

    @Override
    public void from(final A3Rect src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(A3AWTUtils.floatRectangle2D(rectangle2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(rectangle2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return rectangle2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return rectangle2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return rectangle2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return rectangle2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public boolean intersects(final float x, final float y, final float width, final float height) {
        return rectangle2D.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return rectangle2D.intersects(((AWTA3Rect)rect).rectangle2D);
    }
    @Override
    public A3Rect reset() {
        rectangle2D.setRect(0, 0, 0, 0);
        return this;
    }

    @Override
    public boolean intersectsLine(final A3Line line) {
        checkArgNotNull(line, "line");
        return rectangle2D.intersectsLine(((AWTA3Line)line).line2D);
    }

    @Override
    public boolean intersectsLine(final float startX, final float startY, final float endX, final float endY) {
        return rectangle2D.intersectsLine(startX, startY, endX, endY);
    }

}

package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Oval;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Size;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeBounds;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeTopBottom;

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
    public void setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        ellipse2D.width += ellipse2D.x - left;
        ellipse2D.x = left;
    }

    @Override
    public void setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        ellipse2D.height += ellipse2D.y - top;
        ellipse2D.y = top;
    }

    @Override
    public void setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        ellipse2D.width = right - ellipse2D.x;
    }

    @Override
    public void setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        ellipse2D.height = bottom - ellipse2D.y;
    }

    @Override
    public void setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        ellipse2D.x = left;
        ellipse2D.y = top;
        ellipse2D.width = right - left;
        ellipse2D.height = bottom - top;
    }

    @Override
    public void setX(final float x) {
        ellipse2D.x = x;
    }

    @Override
    public void setY(final float y) {
        ellipse2D.y = y;
    }

    @Override
    public void setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        ellipse2D.x = pos.getX();
        ellipse2D.y = pos.getY();
    }

    @Override
    public void setWidth(final float width) {
        ellipse2D.width = width;
    }

    @Override
    public void setHeight(final float height) {
        ellipse2D.height = height;
    }

    @Override
    public void setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        ellipse2D.width = size.getWidth();
        ellipse2D.height = size.getHeight();
    }

    @Override
    public void setPos(final float x, final float y) {
        ellipse2D.x = x;
        ellipse2D.y = y;
    }

    @Override
    public void setSize(final float width, final float height) {
        ellipse2D.width = width;
        ellipse2D.height = height;
    }

    @Override
    public void setRect(final float x, final float y, final float width, final float height) {
        ellipse2D.setFrame(x, y, width, height);
    }

    @Override
    public void setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        ellipse2D.x = pos.getX();
        ellipse2D.y = pos.getY();
        ellipse2D.width = size.getWidth();
        ellipse2D.height = size.getHeight();
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

}

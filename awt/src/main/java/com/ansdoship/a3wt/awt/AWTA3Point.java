package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;

import java.awt.geom.Point2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Point implements A3Point {

    protected final Point2D.Float point2D;

    public AWTA3Point(final Point2D.Float point2D) {
        checkArgNotNull(point2D, "point2D");
        this.point2D = point2D;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    @Override
    public float getX() {
        return point2D.x;
    }

    @Override
    public float getY() {
        return point2D.y;
    }

    @Override
    public void setX(final float x) {
        point2D.x = x;
    }

    @Override
    public void setY(final float y) {
        point2D.y = y;
    }

    @Override
    public void setPos(final float x, final float y) {
        point2D.setLocation(x, y);
    }

    @Override
    public A3Point copy() {
        return new AWTA3Point((Point2D.Float) point2D.clone());
    }

    @Override
    public void to(final A3Point dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Point)dst).point2D.setLocation(point2D);
    }

    @Override
    public void from(final A3Point src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}

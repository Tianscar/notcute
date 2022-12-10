package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Coordinate;

import java.awt.Point;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Coordinate implements A3Coordinate {

    protected final Point point;

    public AWTA3Coordinate(final Point point) {
        checkArgNotNull(point, "point");
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int getX() {
        return point.x;
    }

    @Override
    public int getY() {
        return point.y;
    }

    @Override
    public void setX(final int x) {
        point.x = x;
    }

    @Override
    public void setY(final int y) {
        point.y = y;
    }

    @Override
    public void setPos(final int x, final int y) {
        point.setLocation(x, y);
    }

    @Override
    public A3Coordinate copy() {
        return new AWTA3Coordinate((Point) point.clone());
    }

    @Override
    public void to(final A3Coordinate dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Coordinate)dst).point.setLocation(point);
    }

    @Override
    public void from(final A3Coordinate src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}

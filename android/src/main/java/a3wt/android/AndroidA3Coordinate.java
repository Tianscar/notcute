package a3wt.android;

import android.graphics.Point;
import a3wt.graphics.A3Coordinate;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Coordinate implements A3Coordinate {

    protected final Point point;

    public AndroidA3Coordinate(final Point point) {
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
    public A3Coordinate setX(final int x) {
        point.x = x;
        return this;
    }

    @Override
    public A3Coordinate setY(final int y) {
        point.y = y;
        return this;
    }

    @Override
    public A3Coordinate set(final int x, final int y) {
        point.set(x, y);
        return this;
    }

    @Override
    public A3Coordinate copy() {
        return new AndroidA3Coordinate(new Point(point));
    }

    @Override
    public void to(final A3Coordinate dst) {
        checkArgNotNull(dst, "dst");
        dst.set(point.x, point.y);
    }

    @Override
    public void from(final A3Coordinate src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Coordinate reset() {
        point.set(0, 0);
        return this;
    }

}

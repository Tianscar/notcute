package a3wt.android;

import android.graphics.PointF;
import a3wt.graphics.A3Point;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Point implements A3Point {

    protected final PointF pointF;

    public AndroidA3Point(final PointF pointF) {
        checkArgNotNull(pointF, "pointF");
        this.pointF = pointF;
    }

    @Override
    public float getX() {
        return pointF.x;
    }

    @Override
    public float getY() {
        return pointF.y;
    }

    @Override
    public A3Point setX(final float x) {
        pointF.x = x;
        return this;
    }

    @Override
    public A3Point setY(final float y) {
        pointF.y = y;
        return this;
    }

    @Override
    public A3Point set(final float x, final float y) {
        pointF.set(x, y);
        return this;
    }

    @Override
    public A3Point copy() {
        return new AndroidA3Point(new PointF(pointF.x, pointF.y));
    }

    @Override
    public void to(final A3Point dst) {
        checkArgNotNull(dst, "dst");
        dst.set(pointF.x, pointF.y);
    }

    @Override
    public void from(final A3Point src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Point reset() {
        pointF.set(0, 0);
        return this;
    }

}

package com.ansdoship.a3wt.android;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import com.ansdoship.a3wt.graphics.A3Path;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AndroidA3Path implements A3Path {

    protected final Path path;

    public AndroidA3Path(final Path path) {
        checkArgNotNull(path, "path");
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void reset() {
        path.reset();
    }

    @Override
    public void close() {
        path.close();
    }

    @Override
    public void moveTo(final float x, final float y) {
        path.moveTo(x, y);
    }

    @Override
    public void lineTo(final float x, final float y) {
        path.lineTo(x, y);
    }

    @Override
    public void quadTo(final float x1, final float y1, final float x2, final float y2) {
        path.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        path.cubicTo(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void addPath(final A3Path path) {
        checkArgNotNull(path, "path");
        this.path.addPath(((AndroidA3Path)path).getPath());
    }

    @Override
    public void addArc(final float left, final float top, final float right, final float bottom, final float startAngle, final float sweepAngle) {
        path.addArc(new RectF(left, top, right, bottom), startAngle, sweepAngle);
    }

    @Override
    public void addOval(final float left, final float top, final float right, final float bottom) {
        path.addOval(new RectF(left, top, right, bottom), Path.Direction.CW);
    }

    @Override
    public void addRect(final float left, final float top, final float right, final float bottom) {
        path.addOval(new RectF(left, top, right, bottom), Path.Direction.CW);
    }

    @Override
    public void addRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry) {
        path.addRoundRect(new RectF(left, top, right, bottom), rx, ry, Path.Direction.CW);
    }

    @Override
    public boolean contains(final float x, final float y) {
        final RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        final Region region = new Region();
        region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains((int) x, (int) y);
    }

    @Override
    public A3Path copy() {
        return new AndroidA3Path(new Path(path));
    }

}

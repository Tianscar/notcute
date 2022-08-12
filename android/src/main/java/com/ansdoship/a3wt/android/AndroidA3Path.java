package com.ansdoship.a3wt.android;

import android.graphics.Path;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Path;

public class AndroidA3Path implements A3Path {

    protected final Path path;

    public AndroidA3Path(Path path) {
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
    public void moveTo(float x, float y) {
        path.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        path.lineTo(x, y);
    }

    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        path.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        path.cubicTo(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void addPath(A3Path path) {
        this.path.addPath(((AndroidA3Path)path).getPath());
    }

    @Override
    public void addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        path.addArc(new RectF(left, top, right, bottom), startAngle, sweepAngle);
    }

    @Override
    public void addOval(float left, float top, float right, float bottom) {
        path.addOval(new RectF(left, top, right, bottom), Path.Direction.CW);
    }

    @Override
    public void addRect(float left, float top, float right, float bottom) {
        path.addOval(new RectF(left, top, right, bottom), Path.Direction.CW);
    }

    @Override
    public void addRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        path.addRoundRect(new RectF(left, top, right, bottom), rx, ry, Path.Direction.CW);
    }

    @Override
    public A3Path copy() {
        return new AndroidA3Path(new Path(path));
    }

}

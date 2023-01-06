package com.ansdoship.a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Line;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Rect;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Line implements A3Line {

    protected float startX, startY, endX, endY;

    public AndroidA3Line() {
        reset();
    }

    public AndroidA3Line(final float startX, final float startY, final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public A3Rect getBounds() {
        final float left = Math.min(startX, endX);
        final float top = Math.min(startY, endY);
        final float right = Math.max(startX, endX);
        final float bottom = Math.max(startY, endY);
        return new AndroidA3Rect(new RectF(left, top, right, bottom));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        final float left = Math.min(startX, endX);
        final float top = Math.min(startY, endY);
        final float right = Math.max(startX, endX);
        final float bottom = Math.max(startY, endY);
        bounds.setBounds(left, top, right, bottom);
    }

    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getStartY() {
        return startY;
    }

    @Override
    public float getEndX() {
        return endX;
    }

    @Override
    public float getEndY() {
        return endY;
    }

    @Override
    public A3Point getStartPos() {
        return new AndroidA3Point(new PointF(startX, startY));
    }

    @Override
    public void getStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(startX, startY);
    }

    @Override
    public A3Point getEndPos() {
        return new AndroidA3Point(new PointF(endX, endY));
    }

    @Override
    public void getEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(endX, endY);
    }

    @Override
    public A3Line setStartX(final float startX) {
        this.startX = startX;
        return this;
    }

    @Override
    public A3Line setStartY(final float startY) {
        this.startY = startY;
        return this;
    }

    @Override
    public A3Line setEndX(final float endX) {
        this.endX = endX;
        return this;
    }

    @Override
    public A3Line setEndY(final float endY) {
        this.endY = endY;
        return this;
    }

    @Override
    public A3Line setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        this.startX = pos.getX();
        this.startY = pos.getY();
        return this;
    }

    @Override
    public A3Line setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        this.endX = pos.getX();
        this.endY = pos.getY();
        return this;
    }

    @Override
    public A3Line set(final float startX, final float startY, final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    @Override
    public A3Line set(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        this.startX = startPos.getX();
        this.startY = startPos.getY();
        this.endX = endPos.getX();
        this.endY = endPos.getY();
        return this;
    }

    @Override
    public boolean contains(final float x, final float y) {
        return false;
    }

    @Override
    public boolean contains(final A3Point pos) {
        return false;
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return false;
    }

    @Override
    public boolean contains(final A3Rect rect) {
        return false;
    }

    @Override
    public A3Line copy() {
        return new AndroidA3Line(startX, startY, endX, endY);
    }

    @Override
    public void to(final A3Line dst) {
        checkArgNotNull(dst, "dst");
        dst.set(startX, startY, endX, endY);
    }

    @Override
    public void from(A3Line src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Line reset() {
        startX = startY = endX = endY = 0;
        return this;
    }

}

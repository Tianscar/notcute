package a3wt.android;

import a3wt.graphics.A3QuadCurve;
import android.graphics.PointF;
import android.graphics.RectF;
import a3wt.graphics.A3CubicCurve;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3CubicCurve implements A3CubicCurve {

    protected float startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY;

    public AndroidA3CubicCurve() {
        reset();
    }

    public AndroidA3CubicCurve(float startX, float startY, float ctrlX1, float ctrlY1, float ctrlX2, float ctrlY2, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.ctrlX1 = ctrlX1;
        this.ctrlY1 = ctrlY1;
        this.ctrlX2 = ctrlX2;
        this.ctrlY2 = ctrlY2;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public A3Rect getBounds() {
        final float left = Math.min(Math.min(startX, endX), Math.min(ctrlX1, ctrlX2));
        final float top = Math.min(Math.min(startY, endY), Math.min(ctrlY1, ctrlY2));
        final float right = Math.max(Math.max(startX, endX), Math.max(ctrlX1, ctrlX2));
        final float bottom = Math.max(Math.max(startY, endY), Math.max(ctrlY1, ctrlY2));
        return new AndroidA3Rect(new RectF(left, top, right, bottom));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        final float left = Math.min(Math.min(startX, endX), Math.min(ctrlX1, ctrlX2));
        final float top = Math.min(Math.min(startY, endY), Math.min(ctrlY1, ctrlY2));
        final float right = Math.max(Math.max(startX, endX), Math.max(ctrlX1, ctrlX2));
        final float bottom = Math.max(Math.max(startY, endY), Math.max(ctrlY1, ctrlY2));
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
    public float getCtrlX1() {
        return ctrlX1;
    }

    @Override
    public float getCtrlY1() {
        return ctrlY1;
    }

    @Override
    public float getCtrlX2() {
        return ctrlX2;
    }

    @Override
    public float getCtrlY2() {
        return ctrlY2;
    }

    @Override
    public A3Point getCtrlPos1() {
        return new AndroidA3Point(new PointF(ctrlX1, ctrlY1));
    }

    @Override
    public void getCtrlPos1(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(ctrlX1, ctrlY1);
    }

    @Override
    public A3Point getCtrlPos2() {
        return new AndroidA3Point(new PointF(ctrlX2, ctrlY2));
    }

    @Override
    public void getCtrlPos2(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(ctrlX2, ctrlY2);
    }

    @Override
    public A3CubicCurve setStartX(final float startX) {
        this.startX = startX;
        return this;
    }

    @Override
    public A3CubicCurve setStartY(final float startY) {
        this.startY = startY;
        return this;
    }

    @Override
    public A3CubicCurve setEndX(final float endX) {
        this.endX = endX;
        return this;
    }

    @Override
    public A3CubicCurve setEndY(final float endY) {
        this.endY = endY;
        return this;
    }

    @Override
    public A3CubicCurve setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        startX = pos.getX();
        startY = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        endX = pos.getX();
        endY = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setLine(final float startX, final float startY, final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    @Override
    public A3CubicCurve setLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        return setLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3CubicCurve setCtrlX1(final float ctrlX) {
        ctrlX1 = ctrlX;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlY1(final float ctrlY) {
        ctrlY1 = ctrlY;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlX2(final float ctrlX) {
        ctrlX2 = ctrlX;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlY2(final float ctrlY) {
        ctrlY2 = ctrlY;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlPos1(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        ctrlX1 = pos.getX();
        ctrlY1 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setCtrlPos2(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        ctrlX2 = pos.getX();
        ctrlY2 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve set(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        startX = startPos.getX();
        startY = startPos.getY();
        ctrlX1 = ctrlPos1.getX();
        ctrlY1 = ctrlPos1.getY();
        ctrlX2 = ctrlPos2.getX();
        ctrlY2 = ctrlPos2.getY();
        endX = endPos.getX();
        endY = endPos.getY();
        return this;
    }

    @Override
    public A3CubicCurve set(final float startX, float startY, float ctrlX1, float ctrlY1, float ctrlX2, float ctrlY2, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.ctrlX1 = ctrlX1;
        this.ctrlY1 = ctrlY1;
        this.ctrlX2 = ctrlX2;
        this.ctrlY2 = ctrlY2;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3CubicCurve copy() {
        return new AndroidA3CubicCurve(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
    }

    @Override
    public void to(final A3CubicCurve dst) {
        checkArgNotNull(dst, "dst");
        dst.set(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
    }

    @Override
    public void from(final A3CubicCurve src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3CubicCurve reset() {
        startX = startY = ctrlX1 = ctrlY1 = ctrlX2 = ctrlY2 = endX = endY = 0;
        return this;
    }

}

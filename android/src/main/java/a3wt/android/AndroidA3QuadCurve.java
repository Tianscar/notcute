package a3wt.android;

import android.graphics.PointF;
import android.graphics.RectF;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3QuadCurve;
import a3wt.graphics.A3Rect;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3QuadCurve implements A3QuadCurve {
    
    protected float startX, startY, ctrlX, ctrlY, endX, endY;

    public AndroidA3QuadCurve() {
        reset();
    }

    public AndroidA3QuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, 
                              final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.ctrlX = ctrlX;
        this.ctrlY = ctrlY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public A3Rect getBounds() {
        final float left = Math.min(Math.min(startX, endX), ctrlX);
        final float top = Math.min(Math.min(startY, endY), ctrlY);
        final float right = Math.max(Math.max(startX, endX), ctrlX);
        final float bottom = Math.max(Math.max(startY, endY), ctrlY);
        return new AndroidA3Rect(new RectF(left, top, right, bottom));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        final float left = Math.min(Math.min(startX, endX), ctrlX);
        final float top = Math.min(Math.min(startY, endY), ctrlY);
        final float right = Math.max(Math.max(startX, endX), ctrlX);
        final float bottom = Math.max(Math.max(startY, endY), ctrlY);
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
    public float getCtrlX() {
        return ctrlX;
    }

    @Override
    public float getCtrlY() {
        return ctrlY;
    }

    @Override
    public A3Point getCtrlPos() {
        return new AndroidA3Point(new PointF(ctrlX, ctrlY));
    }

    @Override
    public void getCtrlPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(ctrlX, ctrlY);
    }

    @Override
    public A3QuadCurve setStartX(final float startX) {
        this.startX = startX;
        return this;
    }

    @Override
    public A3QuadCurve setStartY(final float startY) {
        this.startY = startY;
        return this;
    }

    @Override
    public A3QuadCurve setEndX(final float endX) {
        this.endX = endX;
        return this;
    }

    @Override
    public A3QuadCurve setEndY(final float endY) {
        this.endY = endY;
        return this;
    }

    @Override
    public A3QuadCurve setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        this.startX = pos.getX();
        this.startY = pos.getY();
        return this;
    }

    @Override
    public A3QuadCurve setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        this.endX = pos.getX();
        this.endY = pos.getY();
        return this;
    }

    @Override
    public A3QuadCurve setLine(final float startX, final float startY, final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    @Override
    public A3QuadCurve setLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        return setLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3QuadCurve setCtrlX(final float ctrlX) {
        this.ctrlX = ctrlX;
        return this;
    }

    @Override
    public A3QuadCurve setCtrlY(final float ctrlY) {
        this.ctrlY = ctrlY;
        return this;
    }

    @Override
    public A3QuadCurve setCtrlPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        this.ctrlX = pos.getX();
        this.ctrlY = pos.getY();
        return this;
    }

    @Override
    public A3QuadCurve set(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        this.startX = startX;
        this.startY = startY;
        this.ctrlX = ctrlX;
        this.ctrlY = ctrlY;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    @Override
    public A3QuadCurve set(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        this.startX = startPos.getX();
        this.startY = startPos.getY();
        this.ctrlX = ctrlPos.getX();
        this.ctrlY = ctrlPos.getY();
        this.endX = endPos.getX();
        this.endY = endPos.getY();
        return this;
    }

    @Override
    public boolean contains(final float x, final float y) {
        final float kx = startX - 2 * ctrlX + endX;
        final float ky = startY - 2 * ctrlY + endY;
        final float dx = x - startX;
        final float dy = y - startY;
        final float dxl = endX - startX;
        final float dyl = endY - startY;
        final float t0 = (dx * ky - dy * kx) / (dxl * ky - dyl * kx);
        if (t0 < 0 || t0 > 1 || t0 != t0) return false;
        final float xb = kx * t0 * t0 + 2 * (ctrlX - startX) * t0 + startX;
        final float yb = ky * t0 * t0 + 2 * (ctrlY - startY) * t0 + startY;
        final float xl = dxl * t0 + startX;
        final float yl = dyl * t0 + startY;
        return (x >= xb && x < xl) ||
                (x >= xl && x < xb) ||
                (y >= yb && y < yl) ||
                (y >= yl && y < yb);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        if (width <= 0 || height <= 0) return false;
        return contains(x, y) && contains(x + width, y) && contains(x + width, y + height) && contains(x, y + height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public boolean intersects(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3QuadCurve copy() {
        return new AndroidA3QuadCurve(startX, startY, ctrlX, ctrlY, endX, endY);
    }

    @Override
    public void to(final A3QuadCurve dst) {
        checkArgNotNull(dst, "dst");
        dst.set(startX, startY, ctrlX, ctrlY, endX, endY);
    }

    @Override
    public void from(final A3QuadCurve src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3QuadCurve reset() {
        startX = startY = ctrlX = ctrlY = endX = endY = 0;
        return this;
    }
    
}

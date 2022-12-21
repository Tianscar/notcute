package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3QuadCurve;
import com.ansdoship.a3wt.graphics.A3Rect;

import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

import static com.ansdoship.a3wt.awt.A3AWTUtils.floatRectangle2D;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3QuadCurve implements A3QuadCurve {
    
    protected final QuadCurve2D.Float quadCurve2D;
    
    public AWTA3QuadCurve(final QuadCurve2D.Float quadCurve2D) {
        checkArgNotNull(quadCurve2D, "quadCurve2D");
        this.quadCurve2D = quadCurve2D;
    }

    public QuadCurve2D.Float getQuadCurve2D() {
        return quadCurve2D;
    }

    @Override
    public float getStartX() {
        return quadCurve2D.x1;
    }

    @Override
    public float getStartY() {
        return quadCurve2D.y1;
    }

    @Override
    public float getEndX() {
        return quadCurve2D.x2;
    }

    @Override
    public float getEndY() {
        return quadCurve2D.y2;
    }

    @Override
    public A3Point getStartPos() {
        return new AWTA3Point(new Point2D.Float(quadCurve2D.x1, quadCurve2D.y1));
    }

    @Override
    public void getStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(quadCurve2D.x1, quadCurve2D.y1);
    }

    @Override
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(quadCurve2D.x2, quadCurve2D.y2));
    }

    @Override
    public void getEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(quadCurve2D.x2, quadCurve2D.y2);
    }

    @Override
    public float getCtrlX() {
        return quadCurve2D.ctrlx;
    }

    @Override
    public float getCtrlY() {
        return quadCurve2D.ctrly;
    }

    @Override
    public A3Point getCtrlPos() {
        return new AWTA3Point(new Point2D.Float(quadCurve2D.ctrlx, quadCurve2D.ctrly));
    }

    @Override
    public void getCtrlPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(quadCurve2D.ctrlx, quadCurve2D.ctrly);
    }

    @Override
    public A3QuadCurve setStartX(final float startX) {
        quadCurve2D.x1 = startX;
        return this;
    }

    @Override
    public A3QuadCurve setStartY(final float startY) {
        quadCurve2D.y1 = startY;
        return this;
    }

    @Override
    public A3QuadCurve setEndX(final float endX) {
        quadCurve2D.x2 = endX;
        return this;
    }

    @Override
    public A3QuadCurve setEndY(final float endY) {
        quadCurve2D.y2 = endY;
        return this;
    }

    @Override
    public A3QuadCurve setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.x1 = pos.getX();
        quadCurve2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3QuadCurve setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.x1 = pos.getX();
        quadCurve2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3QuadCurve setCtrlX(final float ctrlX) {
        quadCurve2D.ctrlx = ctrlX;
        return this;
    }

    @Override
    public A3QuadCurve setCtrlY(float ctrlY) {
        quadCurve2D.ctrly = ctrlY;
        return this;
    }

    @Override
    public A3QuadCurve setCtrlPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.ctrlx = pos.getX();
        quadCurve2D.ctrly = pos.getY();
        return this;
    }

    @Override
    public void set(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        quadCurve2D.setCurve(startX, startY, ctrlX, ctrlY, endX, endY);
    }

    @Override
    public void set(A3Point startPos, A3Point ctrlPos, A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        quadCurve2D.setCurve(((AWTA3Point)startPos).point2D, ((AWTA3Point)ctrlPos).point2D, ((AWTA3Point)endPos).point2D);
    }

    @Override
    public A3QuadCurve copy() {
        return new AWTA3QuadCurve((QuadCurve2D.Float) quadCurve2D.clone());
    }

    @Override
    public void to(final A3QuadCurve dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3QuadCurve)dst).quadCurve2D.setCurve(quadCurve2D);
    }

    @Override
    public void from(final A3QuadCurve src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(quadCurve2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(quadCurve2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return quadCurve2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return quadCurve2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return quadCurve2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return quadCurve2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public void reset() {
        quadCurve2D.setCurve(0, 0, 0, 0, 0, 0);
    }

}

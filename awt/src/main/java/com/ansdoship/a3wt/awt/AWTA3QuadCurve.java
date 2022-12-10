package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3QuadCurve;

import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

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
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(quadCurve2D.x2, quadCurve2D.y2));
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
    public void setStartX(final float startX) {
        quadCurve2D.x1 = startX;
    }

    @Override
    public void setStartY(final float startY) {
        quadCurve2D.y1 = startY;
    }

    @Override
    public void setEndX(final float endX) {
        quadCurve2D.x2 = endX;
    }

    @Override
    public void setEndY(final float endY) {
        quadCurve2D.y2 = endY;
    }

    @Override
    public void setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.x1 = pos.getX();
        quadCurve2D.y1 = pos.getY();
    }

    @Override
    public void setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.x1 = pos.getX();
        quadCurve2D.y1 = pos.getY();
    }

    @Override
    public void setCtrlX(final float ctrlX) {
        quadCurve2D.ctrlx = ctrlX;
    }

    @Override
    public void setCtrlY(float ctrlY) {
        quadCurve2D.ctrly = ctrlY;
    }

    @Override
    public void setCtrlPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        quadCurve2D.ctrlx = pos.getX();
        quadCurve2D.ctrly = pos.getY();
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
    
}

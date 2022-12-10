package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3CubicCurve;
import com.ansdoship.a3wt.graphics.A3Point;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3CubicCurve implements A3CubicCurve {

    protected final CubicCurve2D.Float cubicCurve2D;
    
    public AWTA3CubicCurve(final CubicCurve2D.Float cubicCurve2D) {
        checkArgNotNull(cubicCurve2D, "cubicCurve2D");
        this.cubicCurve2D = cubicCurve2D;
    }

    public CubicCurve2D.Float getCubicCurve2D() {
        return cubicCurve2D;
    }

    @Override
    public float getStartX() {
        return cubicCurve2D.x1;
    }

    @Override
    public float getStartY() {
        return cubicCurve2D.y1;
    }

    @Override
    public float getEndX() {
        return cubicCurve2D.x2;
    }

    @Override
    public float getEndY() {
        return cubicCurve2D.y2;
    }

    @Override
    public A3Point getStartPos() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.x1, cubicCurve2D.y1));
    }

    @Override
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.x2, cubicCurve2D.y2));
    }

    @Override
    public float getCtrlX1() {
        return cubicCurve2D.ctrlx1;
    }

    @Override
    public float getCtrlY1() {
        return cubicCurve2D.ctrly1;
    }

    @Override
    public float getCtrlX2() {
        return cubicCurve2D.ctrlx2;
    }

    @Override
    public float getCtrlY2() {
        return cubicCurve2D.ctrly2;
    }

    @Override
    public A3Point getCtrlPos1() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.ctrlx1, cubicCurve2D.ctrly1));
    }

    @Override
    public A3Point getCtrlPos2() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.ctrlx2, cubicCurve2D.ctrly2));
    }

    @Override
    public void setStartX(final float startX) {
        cubicCurve2D.x1 = startX;
    }

    @Override
    public void setStartY(final float startY) {
        cubicCurve2D.y1 = startY;
    }

    @Override
    public void setEndX(final float endX) {
        cubicCurve2D.x2 = endX;
    }

    @Override
    public void setEndY(final float endY) {
        cubicCurve2D.y2 = endY;
    }

    @Override
    public void setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.x1 = pos.getX();
        cubicCurve2D.y1 = pos.getY();
    }

    @Override
    public void setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.x1 = pos.getX();
        cubicCurve2D.y1 = pos.getY();
    }

    @Override
    public void setCtrlX1(final float ctrlX) {
        cubicCurve2D.ctrlx1 = ctrlX;
    }

    @Override
    public void setCtrlX2(final float ctrlX) {
        cubicCurve2D.ctrlx2 = ctrlX;
    }

    @Override
    public void setCtrlY1(final float ctrlY) {
        cubicCurve2D.ctrly1 = ctrlY;
    }

    @Override
    public void setCtrlY2(final float ctrlY) {
        cubicCurve2D.ctrly2 = ctrlY;
    }

    @Override
    public void setCtrlPos1(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.ctrlx1 = pos.getX();
        cubicCurve2D.ctrly1 = pos.getY();
    }

    @Override
    public void setCtrlPos2(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.ctrlx2 = pos.getX();
        cubicCurve2D.ctrly2 = pos.getY();
    }

    @Override
    public A3CubicCurve copy() {
        return new AWTA3CubicCurve((CubicCurve2D.Float) cubicCurve2D.clone());
    }

    @Override
    public void to(final A3CubicCurve dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3CubicCurve)dst).cubicCurve2D.setCurve(cubicCurve2D);
    }

    @Override
    public void from(final A3CubicCurve src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }
    
}

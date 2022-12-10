package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Line;
import com.ansdoship.a3wt.graphics.A3Point;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Line implements A3Line {

    protected final Line2D.Float line2D;

    public AWTA3Line(final Line2D.Float line2D) {
        checkArgNotNull(line2D, "line2D");
        this.line2D = line2D;
    }

    public Line2D.Float getLine2D() {
        return line2D;
    }

    @Override
    public float getStartX() {
        return line2D.x1;
    }

    @Override
    public float getStartY() {
        return line2D.y1;
    }

    @Override
    public float getEndX() {
        return line2D.x2;
    }

    @Override
    public float getEndY() {
        return line2D.y2;
    }

    @Override
    public A3Point getStartPos() {
        return new AWTA3Point(new Point2D.Float(line2D.x1, line2D.y1));
    }

    @Override
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(line2D.x2, line2D.y2));
    }

    @Override
    public void setStartX(final float startX) {
        line2D.x1 = startX;
    }

    @Override
    public void setStartY(final float startY) {
        line2D.y1 = startY;
    }

    @Override
    public void setEndX(final float endX) {
        line2D.x2 = endX;
    }

    @Override
    public void setEndY(final float endY) {
        line2D.y2 = endY;
    }

    @Override
    public void setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        line2D.x1 = pos.getX();
        line2D.y1 = pos.getY();
    }

    @Override
    public void setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        line2D.x1 = pos.getX();
        line2D.y1 = pos.getY();
    }

    @Override
    public A3Line copy() {
        return new AWTA3Line((Line2D.Float) line2D.clone());
    }

    @Override
    public void to(final A3Line dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Line)dst).line2D.setLine(line2D);
    }

    @Override
    public void from(final A3Line src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}

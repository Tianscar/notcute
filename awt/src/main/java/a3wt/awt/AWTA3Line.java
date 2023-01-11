package a3wt.awt;

import a3wt.graphics.A3Line;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Rect;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static a3wt.awt.A3AWTUtils.floatRectangle2D;
import static a3wt.util.A3Preconditions.checkArgNotNull;

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
    public void getStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(line2D.x1, line2D.y1);
    }

    @Override
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(line2D.x2, line2D.y2));
    }

    @Override
    public void getEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(line2D.x2, line2D.y2);
    }

    @Override
    public A3Line setStartX(final float startX) {
        line2D.x1 = startX;
        return this;
    }

    @Override
    public A3Line setStartY(final float startY) {
        line2D.y1 = startY;
        return this;
    }

    @Override
    public A3Line setEndX(final float endX) {
        line2D.x2 = endX;
        return this;
    }

    @Override
    public A3Line setEndY(final float endY) {
        line2D.y2 = endY;
        return this;
    }

    @Override
    public A3Line setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        line2D.x1 = pos.getX();
        line2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3Line setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        line2D.x1 = pos.getX();
        line2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3Line set(final float startX, final float startY, final float endX, final float endY) {
        line2D.setLine(startX, startY, endX, endY);
        return this;
    }

    @Override
    public A3Line set(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        line2D.setLine(((AWTA3Point)startPos).point2D, ((AWTA3Point)endPos).point2D);
        return this;
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

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(line2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(line2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return line2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return line2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return line2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return line2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public A3Line reset() {
        line2D.setLine(0, 0, 0, 0);
        return this;
    }

}

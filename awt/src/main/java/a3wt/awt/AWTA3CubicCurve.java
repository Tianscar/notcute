package a3wt.awt;

import a3wt.graphics.A3CubicCurve;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3QuadCurve;
import a3wt.graphics.A3Rect;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

import static a3wt.awt.A3AWTUtils.floatRectangle2D;
import static a3wt.util.A3Preconditions.checkArgNotNull;

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
    public void getStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(cubicCurve2D.x1, cubicCurve2D.y1);
    }

    @Override
    public A3Point getEndPos() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.x2, cubicCurve2D.y2));
    }

    @Override
    public void getEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(cubicCurve2D.x2, cubicCurve2D.y2);
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
    public void getCtrlPos1(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(cubicCurve2D.ctrlx1, cubicCurve2D.ctrly1);
    }

    @Override
    public A3Point getCtrlPos2() {
        return new AWTA3Point(new Point2D.Float(cubicCurve2D.ctrlx2, cubicCurve2D.ctrly2));
    }

    @Override
    public void getCtrlPos2(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(cubicCurve2D.ctrlx2, cubicCurve2D.ctrly2);
    }

    @Override
    public A3CubicCurve setStartX(final float startX) {
        cubicCurve2D.x1 = startX;
        return this;
    }

    @Override
    public A3CubicCurve setStartY(final float startY) {
        cubicCurve2D.y1 = startY;
        return this;
    }

    @Override
    public A3CubicCurve setEndX(final float endX) {
        cubicCurve2D.x2 = endX;
        return this;
    }

    @Override
    public A3CubicCurve setEndY(final float endY) {
        cubicCurve2D.y2 = endY;
        return this;
    }

    @Override
    public A3CubicCurve setStartPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.x1 = pos.getX();
        cubicCurve2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setEndPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.x1 = pos.getX();
        cubicCurve2D.y1 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setLine(final float startX, final float startY, final float endX, final float endY) {
        cubicCurve2D.x1 = startX;
        cubicCurve2D.y1 = startY;
        cubicCurve2D.x2 = endX;
        cubicCurve2D.y2 = endY;
        return this;
    }

    @Override
    public A3CubicCurve setLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        cubicCurve2D.x1 = startPos.getX();
        cubicCurve2D.y1 = startPos.getY();
        cubicCurve2D.x2 = endPos.getX();
        cubicCurve2D.y2 = endPos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setCtrlX1(final float ctrlX) {
        cubicCurve2D.ctrlx1 = ctrlX;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlX2(final float ctrlX) {
        cubicCurve2D.ctrlx2 = ctrlX;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlY1(final float ctrlY) {
        cubicCurve2D.ctrly1 = ctrlY;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlY2(final float ctrlY) {
        cubicCurve2D.ctrly2 = ctrlY;
        return this;
    }

    @Override
    public A3CubicCurve setCtrlPos1(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.ctrlx1 = pos.getX();
        cubicCurve2D.ctrly1 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve setCtrlPos2(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        cubicCurve2D.ctrlx2 = pos.getX();
        cubicCurve2D.ctrly2 = pos.getY();
        return this;
    }

    @Override
    public A3CubicCurve set(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        cubicCurve2D.setCurve(((AWTA3Point)startPos).point2D, ((AWTA3Point)ctrlPos1).point2D,
                ((AWTA3Point)ctrlPos2).point2D, ((AWTA3Point)endPos).point2D);
        return this;
    }

    @Override
    public A3CubicCurve set(final float startX, final float startY, final float ctrlX1, final float ctrlY1,
                    final float ctrlX2, final float ctrlY2, final float endX, final float endY) {
        cubicCurve2D.setCurve(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
        return this;
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

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(cubicCurve2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(cubicCurve2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return cubicCurve2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return cubicCurve2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return cubicCurve2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return cubicCurve2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public boolean intersects(final float x, final float y, final float width, final float height) {
        return cubicCurve2D.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return cubicCurve2D.intersects(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public A3CubicCurve reset() {
        cubicCurve2D.setCurve(0, 0, 0, 0, 0, 0, 0, 0);
        return this;
    }

}

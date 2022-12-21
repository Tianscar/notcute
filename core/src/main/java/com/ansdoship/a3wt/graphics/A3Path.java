package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Path extends A3Copyable<A3Path>, A3Boundable, A3Resetable {

    void close();

    A3Path moveTo(final float x, final float y);
    A3Path moveTo(final A3Point pos);
    A3Path lineTo(final float x, final float y);
    A3Path lineTo(final A3Point pos);
    A3Path quadTo(final float x1, final float y1, final float x2, final float y2);
    A3Path quadTo(final A3Point pos1, final A3Point pos2);
    A3Path cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3);
    A3Path cubicTo(final A3Point pos1, final A3Point pos2, final A3Point pos3);

    A3Path addPath(final A3Path path);
    A3Path addArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle);
    A3Path addArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle);
    A3Path addArc(final A3Rect rect, final float startAngle, final float sweepAngle);
    A3Path addArc(final A3Arc arc);
    A3Path addLine(final float startX, final float startY, final float endX, final float endY);
    A3Path addLine(final A3Point startPos, final A3Point endPos);
    A3Path addLine(final A3Line line);
    A3Path addQuadCurve(final float startX, final float startY, final float endX, final float endY, final float ctrlX, final float ctrlY);
    A3Path addQuadCurve(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos);
    A3Path addQuadCurve(final A3QuadCurve quadCurve);
    A3Path addCubicCurve(final float startX, final float startY, final float endX, final float endY,
                         final float ctrlX1, final float ctrlY1, final float ctrlX2, final float ctrlY2);
    A3Path addCubicCurve(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos1, final A3Point ctrlPos2);
    A3Path addCubicCurve(final A3CubicCurve cubicCurve);
    A3Path addOval(final float x, final float y, final float width, final float height);
    A3Path addOval(final A3Point pos, final A3Size size);
    A3Path addOval(final A3Rect rect);
    A3Path addOval(final A3Oval oval);
    A3Path addRect(final float x, final float y, final float width, final float height);
    A3Path addRect(final A3Point pos, final A3Size size);
    A3Path addRect(final A3Rect rect);
    A3Path addRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry);
    A3Path addRoundRect(final A3Point pos, final A3Size size, final A3Size corner);
    A3Path addRoundRect(final A3RoundRect roundRect);

    boolean contains(final float x, final float y);
    boolean contains(final A3Point pos);

    boolean contains(final float x, final float y, final float width, final float height);
    boolean contains(final A3Rect rect);

}

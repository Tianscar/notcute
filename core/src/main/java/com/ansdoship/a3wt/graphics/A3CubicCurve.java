package com.ansdoship.a3wt.graphics;

public interface A3CubicCurve extends A3Shape<A3CubicCurve> {

    float getStartX();
    float getStartY();
    float getEndX();
    float getEndY();
    A3Point getStartPos();
    A3Point getEndPos();
    float getCtrlX1();
    float getCtrlY1();
    float getCtrlX2();
    float getCtrlY2();
    A3Point getCtrlPos1();
    A3Point getCtrlPos2();

    void get(final float[] values);

    A3CubicCurve setStartX(final float startX);
    A3CubicCurve setStartY(final float startY);
    A3CubicCurve setEndX(final float endX);
    A3CubicCurve setEndY(final float endY);
    A3CubicCurve setStartPos(final A3Point pos);
    A3CubicCurve setEndPos(final A3Point pos);
    A3CubicCurve setCtrlX1(final float ctrlX);
    A3CubicCurve setCtrlY1(final float ctrlY);
    A3CubicCurve setCtrlX2(final float ctrlX);
    A3CubicCurve setCtrlY2(final float ctrlY);
    A3CubicCurve setCtrlPos1(final A3Point pos);
    A3CubicCurve setCtrlPos2(final A3Point pos);

    void set(final float[] values);
    void set(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos1, final A3Point ctrlPos2);
    void set(final float startX, final float startY, final float endX, final float endY,
             final float ctrlX1, final float ctrlY1, final float ctrlX2, final float ctrlY2);
    
}

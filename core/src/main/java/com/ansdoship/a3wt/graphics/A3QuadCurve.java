package com.ansdoship.a3wt.graphics;

public interface A3QuadCurve extends A3Shape<A3QuadCurve> {

    float getStartX();
    float getStartY();
    float getEndX();
    float getEndY();
    A3Point getStartPos();
    A3Point getEndPos();
    float getCtrlX();
    float getCtrlY();
    A3Point getCtrlPos();

    void get(final float[] values);

    A3QuadCurve setStartX(final float startX);
    A3QuadCurve setStartY(final float startY);
    A3QuadCurve setEndX(final float endX);
    A3QuadCurve setEndY(final float endY);
    A3QuadCurve setStartPos(final A3Point pos);
    A3QuadCurve setEndPos(final A3Point pos);
    A3QuadCurve setCtrlX(final float ctrlX);
    A3QuadCurve setCtrlY(final float ctrlY);
    A3QuadCurve setCtrlPos(final A3Point pos);

    void set(final float[] values);
    void set(final float startX, final float startY, final float endX, final float endY, final float ctrlX, final float ctrlY);
    void set(final A3Point startPos, final A3Point endPos, final A3Point ctrlPos);
    
}

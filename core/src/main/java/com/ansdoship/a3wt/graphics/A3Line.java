package com.ansdoship.a3wt.graphics;

public interface A3Line extends A3Shape<A3Line> {

    float getStartX();
    float getStartY();
    float getEndX();
    float getEndY();
    A3Point getStartPos();
    A3Point getEndPos();

    void get(final float[] values);

    A3Line setStartX(final float startX);
    A3Line setStartY(final float startY);
    A3Line setEndX(final float endX);
    A3Line setEndY(final float endY);
    A3Line setStartPos(final A3Point pos);
    A3Line setEndPos(final A3Point pos);

    void set(final float[] values);
    void set(final float startX, final float startY, final float endX, final float endY);
    void set(final A3Point startPos, final A3Point endPos);
    
}

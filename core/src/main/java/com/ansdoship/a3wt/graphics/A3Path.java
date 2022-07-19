package com.ansdoship.a3wt.graphics;

public interface A3Path {

    void reset();
    void close();
    void moveTo(float x, float y);
    void lineTo(float x, float y);
    void quadTo(float x1, float y1, float x2, float y2);
    void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3);

    void addPath(A3Path path);
    void addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle);
    void addOval(float left, float top, float right, float bottom);
    void addRect(float left, float top, float right, float bottom);
    void addRoundRect(float left, float top, float right, float bottom, float rx, float ry);

}

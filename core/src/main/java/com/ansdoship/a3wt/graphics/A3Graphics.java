package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Disposable;

public interface A3Graphics extends A3Disposable {

    int getWidth();
    int getHeight();

    void drawPath(A3Path path);
    void drawImage(A3Image image, int x, int y);
    void drawPoint(float x, float y);
    void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter);
    void drawLine(float startX, float startY, float stopX, float stopY);
    void drawOval(float left, float top, float right, float bottom);
    void drawRect(float left, float top, float right, float bottom);
    void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry);
    void drawText(String text, float x, float y);

}

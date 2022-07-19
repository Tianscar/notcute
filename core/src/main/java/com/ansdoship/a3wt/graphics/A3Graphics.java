package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Disposable;

public interface A3Graphics extends A3Disposable {

    int getWidth();
    int getHeight();

    void drawImage(A3Image image, int x, int y);
    void drawArc(int left, int top, int right, int bottom, int startAngle, int sweepAngle);
    void drawLine(int startX, int startY, int stopX, int stopY);
    void drawOval(int left, int top, int right, int bottom);
    void drawRect(int left, int top, int right, int bottom);
    void drawRoundRect(int left, int top, int right, int bottom, int rx, int ry);
    void drawText(String text, float x, float y);
    void drawPolygon(int[] xpoints, int[] ypoints, int npoints, boolean close);

}

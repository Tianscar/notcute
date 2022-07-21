package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Disposable;

public interface A3Graphics extends A3Disposable {

    class Style {
        private Style(){}
        public static final int STROKE = 0;
        public static final int FILL = 1;
    }

    class Join {
        public static final int MITER = 0;
        public static final int ROUND = 1;
        public static final int BEVEL = 2;
    }

    class Cap {
        public static final int BUTT = 0;
        public static final int ROUND = 1;
        public static final int SQUARE = 2;
    }

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

    int getColor();
    void setColor(int color);

    int getStyle();
    void setStyle(int style);

    float getStrokeWidth();
    void setStrokeWidth(float width);

    int getStrokeJoin();
    void setStrokeJoin(int join);

    int getStrokeCap();
    void setStrokeCap(int cap);

    float getStrokeMiter();
    void setStrokeMiter(float miter);

}

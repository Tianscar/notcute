package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.input.A3CanvasListener;
import com.ansdoship.a3wt.util.A3Disposable;

import java.util.List;

public interface A3Canvas extends A3Disposable {

    A3Context getA3Context();
    A3Graphics getA3Graphics();

    int getWidth();
    int getHeight();
    int getBackgroundColor();
    void setBackgroundColor(int color);

    long elapsed();
    void paint(A3Graphics graphics);
    void update();

    A3Image snapshot();
    A3Image snapshotBuffer();

    List<A3CanvasListener> getA3CanvasListeners();
    void addA3CanvasListener(A3CanvasListener listener);

}

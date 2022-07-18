package com.ansdoship.a3wt.android;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Graphics;

public class A3SurfaceView extends SurfaceView implements A3Canvas, SurfaceHolder.Callback {

    protected volatile long elapsed = 0;
    protected AndroidA3Graphics graphics = new A3SurfaceViewGraphics();

    protected SurfaceHolder surfaceHolder;
    protected boolean destroyed = false;

    private static class A3SurfaceViewGraphics extends AndroidA3Graphics {
        public A3SurfaceViewGraphics() {
            super((Canvas) null);
        }
        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }
    }

    public A3SurfaceView(Context context) {
        this(context, null);
    }

    public A3SurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public A3SurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        destroyed = false;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        destroyed = true;
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
    }

    @Override
    public synchronized void update() {
        long time = System.currentTimeMillis();
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null) return;
        try {
            graphics.setCanvas(canvas);
            paint(graphics);
            canvas.save();
            canvas.restore();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            surfaceHolder.unlockCanvasAndPost(canvas);
            graphics.setCanvas(null);
        }
        long now = System.currentTimeMillis();
        elapsed = now - time;
    }

}

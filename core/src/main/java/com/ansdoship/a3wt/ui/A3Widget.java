package com.ansdoship.a3wt.ui;

import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.input.A3InputListener;
import com.ansdoship.a3wt.util.A3Colors;
import com.ansdoship.a3wt.util.A3Paintable;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Widget implements A3Paintable, A3InputListener {

    protected final A3Context.Handle handle;
    protected final A3GraphicsKit graphicsKit;
    protected final A3Rect rect;
    protected int backgroundColor = A3Colors.BLACK;

    public A3Widget(final A3Context.Handle handle) {
        checkArgNotNull(handle, "handle");
        this.handle = handle;
        graphicsKit = handle.getGraphicsKit();
        rect = graphicsKit.createRect();
    }

    public A3Rect getRect() {
        return rect;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void paint(final A3Graphics graphics, final boolean snapshot) {
        graphics.save();
        paintBackground(graphics, snapshot);
        paintContent(graphics, snapshot);
        graphics.restore();
    }

    protected void paintBackground(final A3Graphics graphics, final boolean snapshot) {
        graphics.setStyle(A3Graphics.Style.FILL);
        graphics.setColor(backgroundColor);
        graphics.drawRect(rect);
    }

    protected void paintContent(final A3Graphics graphics, final boolean snapshot) {
    }

    @Override
    public boolean keyDown(int keyCode, int keyLocation) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode, int keyLocation) {
        return false;
    }

    @Override
    public boolean keyTyped(char keyChar) {
        return false;
    }

    @Override
    public boolean pointerDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean pointerUp(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean pointerDragged(float x, float y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(float x, float y) {
        return false;
    }

    @Override
    public boolean mouseEntered(float x, float y) {
        return false;
    }

    @Override
    public boolean mouseExited(float x, float y) {
        return false;
    }

    @Override
    public boolean mouseWheelScrolled(float amount, int scrollType) {
        return false;
    }

}

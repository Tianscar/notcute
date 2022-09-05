package com.ansdoship.a3wt.input;

public abstract class A3InputAdapter implements A3InputListener {

    @Override
    public boolean keyDown(int keyCode, int keyLocation) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode, int keyLocation) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
    public boolean mouseWheelScrolled(float amount, int scrollType) {
        return false;
    }

}

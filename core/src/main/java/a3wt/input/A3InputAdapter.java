package a3wt.input;

public abstract class A3InputAdapter implements A3InputListener {

    @Override
    public boolean keyDown(final int keyCode, final int keyLocation) {
        return false;
    }

    @Override
    public boolean keyUp(final int keyCode, final int keyLocation) {
        return false;
    }

    @Override
    public boolean keyTyped(final char keyChar) {
        return false;
    }

    @Override
    public boolean pointerDown(final float x, final float y, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean pointerUp(final float x, final float y, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean pointerDragged(final float x, final float y, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final float x, final float y) {
        return false;
    }

    @Override
    public boolean mouseEntered(final float x, final float y) {
        return false;
    }

    @Override
    public boolean mouseExited(final float x, final float y) {
        return false;
    }

    @Override
    public boolean mouseWheelScrolled(final float amount, final int scrollType) {
        return false;
    }

}

package com.ansdoship.a3wt.input;

public interface A3InputListener {

    class Button {
        private Button(){}
        public static final int LEFT = 0;
        public static final int MIDDLE = 1;
        public static final int RIGHT = 2;
    }

    class ScrollType {
        private ScrollType(){}
        public static final int UNIT = 0;
        public static final int BLOCK = 1;
    }

    boolean keyDown(int keyCode);
    boolean keyUp(int keyCode);
    boolean keyTyped(char keyChar);

    boolean pointerDown(float x, float y, int pointer, int button);
    boolean pointerUp(float x, float y, int pointer, int button);
    boolean pointerDragged(float x, float y, int pointer, int button);

    boolean mouseMoved(float x, float y);
    boolean mouseWheelScrolled(float amount, int scrollType);

}

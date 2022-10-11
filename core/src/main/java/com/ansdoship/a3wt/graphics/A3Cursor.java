package com.ansdoship.a3wt.graphics;

public interface A3Cursor {

    class Type {
        public static final int ARROW = 0;
        public static final int DEFAULT = ARROW;
        public static final int CROSSHAIR = 1;
        public static final int IBEAM = 2;
        public static final int WAIT = 3;
        public static final int HAND = 4;
        public static final int MOVE = 5;
        public static final int RESIZE_N = 6;
        public static final int RESIZE_S = 7;
        public static final int RESIZE_W = 8;
        public static final int RESIZE_E = 9;
        public static final int RESIZE_SW = 10;
        public static final int RESIZE_SE = 11;
        public static final int RESIZE_NW = 12;
        public static final int RESIZE_NE = 13;
        public static final int RESIZE_NS = 14;
        public static final int RESIZE_WE = 15;
        public static final int RESIZE_NWSE = 16;
        public static final int RESIZE_NESW = 17;
        public static final int GRAB = 18;
        public static final int GRABBING = 19;
        public static final int HELP = 20;
        public static final int NO = 21;
        public static final int ZOOM_IN = 22;
        public static final int ZOOM_OUT = 23;
        public static final int GONE = 100;
    }

    int getType();
    A3Image getImage();

}

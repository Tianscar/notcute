package io.notcute.ui;

import io.notcute.g2d.Image;
import io.notcute.util.SwapCloneable;

public interface Cursor {

    final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
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
        public static final int CELL = 18;
        public static final int HELP = 19;
        public static final int ZOOM_IN = 20;
        public static final int ZOOM_OUT = 21;
        public static final int NO = 22;
        public static final int GRAB = 23;
        public static final int GRABBING = 24;
        public static final int COPY_DROP = 25;
        public static final int LINK_DROP = 26;
        public static final int MOVE_DROP = 27;
        public static final int NO_DROP = 28;
        public static final int UP_ARROW = 29;
        public static final int VERTICAL_IBEAM = 30;
        public static final int CONTEXT_MENU = 31;
        public static final int PROGRESS = 32;
        public static final int NONE = 100;
    }

    class Frame implements SwapCloneable {

        private long duration;
        private Cursor cursor;

        public Frame(Cursor cursor, long duration) {
            this.duration = duration;
            this.cursor = cursor;
        }

        public Frame(Cursor cursor) {
            this.duration = 0;
            this.cursor = cursor;
        }

        public Frame(Frame frame) {
            this(frame.getCursor(), frame.getDuration());
        }

        public void setFrame(Frame frame) {
            setFrame(frame.getCursor(), frame.getDuration());
        }

        public void setFrame(Cursor cursor, long duration) {
            this.cursor = cursor;
            this.duration = duration;
        }
        
        public void setCursor(Cursor cursor) {
            this.cursor = cursor;
        }
        
        public Cursor getCursor() {
            return cursor;
        }
        
        public void setDuration(long duration) {
            this.duration = duration;
        }
        
        public long getDuration() {
            return duration;
        }

        public Object clone() {
            try {
                return super.clone();
            }
            catch (CloneNotSupportedException e) {
                return new Frame(this);
            }
        }

        @Override
        public void to(Object dst) {
            Frame frame = (Frame) dst;
            frame.setFrame(this);
        }

    }

    int getType();
    Image getImage();

    int getHotSpotX();
    int getHotSpotY();

}

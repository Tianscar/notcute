package a3wt.graphics;

import a3wt.util.A3Copyable;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Cursor {

    class Type {
        private Type() {}
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

    interface Frame extends A3Copyable<Frame> {
        void setCursor(final A3Cursor cursor);
        A3Cursor getCursor();
        void setDuration(final long duration);
        long getDuration();
    }

    class DefaultFrame implements Frame {

        protected long duration;
        protected A3Cursor cursor;

        public DefaultFrame(final A3Cursor cursor, final long duration) {
            checkArgNotNull(cursor, "cursor");
            this.duration = duration;
            this.cursor = cursor;
        }

        public DefaultFrame(final A3Cursor cursor) {
            checkArgNotNull(cursor, "cursor");
            this.duration = 0;
            this.cursor = cursor;
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            checkArgNotNull(cursor, "cursor");
            this.cursor = cursor;
        }

        @Override
        public A3Cursor getCursor() {
            return cursor;
        }

        @Override
        public void setDuration(final long duration) {
            this.duration = duration;
        }

        @Override
        public long getDuration() {
            return duration;
        }

        @Override
        public Frame copy() {
            return new DefaultFrame(cursor, duration);
        }

        @Override
        public void to(final Frame dst) {
            checkArgNotNull(dst, "dst");
            dst.setDuration(duration);
            dst.setCursor(cursor);
        }

        @Override
        public void from(final Frame src) {
            checkArgNotNull(src, "src");
            src.to(this);
        }

    }

    int getType();
    A3Image getImage();

    int getHotSpotX();
    int getHotSpotY();
    A3Coordinate getHotSpot();

}

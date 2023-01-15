package a3wt.graphics;

import a3wt.util.A3MutableCopyable;
import a3wt.util.A3FilterList;

public interface A3FramedCursor extends A3Cursor, A3MutableCopyable<A3FramedCursor>, A3FilterList<A3Cursor.Frame> {

    int getIndex();
    A3FramedCursor setIndex(final int index);

    A3Cursor.Frame get();

    default long getDuration() {
        return get().getDuration();
    }

    default void setDuration(final long duration) {
        get().setDuration(duration);
    }

    default A3Cursor getCursor() {
        return get().getCursor();
    }

    default void setCursor(final A3Cursor cursor) {
        get().setCursor(cursor);
    }

}

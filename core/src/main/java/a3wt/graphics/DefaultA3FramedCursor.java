package a3wt.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3FramedCursor implements A3FramedCursor {

    protected final List<Frame> frames = new ArrayList<>();
    protected volatile int index = 0;
    protected volatile boolean disposed = false;

    public DefaultA3FramedCursor(final Collection<Frame> frames) {
        for (Frame frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedCursor(final Frame... frames) {
        for (Frame frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedCursor(final Iterator<Frame> frames) {
        Frame frame;
        while (frames.hasNext()) {
            frame = frames.next();
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    @Override
    public int getType() {
        return get().getCursor().getType();
    }

    @Override
    public A3Image getImage() {
        return get().getCursor().getImage();
    }

    @Override
    public int getHotSpotX() {
        return get().getCursor().getHotSpotX();
    }

    @Override
    public int getHotSpotY() {
        return get().getCursor().getHotSpotY();
    }

    @Override
    public A3Coordinate getHotSpot() {
        return get().getCursor().getHotSpot();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public A3FramedCursor setIndex(final int index) {
        this.index = index;
        return this;
    }

    @Override
    public A3Cursor.Frame get() {
        return frames.get(index);
    }

    @Override
    public List<A3Cursor.Frame> filterInstance() {
        return frames;
    }

    @Override
    public A3FramedCursor copy() {
        return new DefaultA3FramedCursor(frames);
    }

    @Override
    public void to(A3FramedCursor dst) {
        checkArgNotNull(dst, "dst");
        ((DefaultA3FramedCursor) dst).frames.clear();
        ((DefaultA3FramedCursor) dst).frames.addAll(frames);
    }

    @Override
    public void from(final A3FramedCursor src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}

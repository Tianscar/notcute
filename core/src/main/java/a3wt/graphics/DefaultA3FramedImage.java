package a3wt.graphics;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static a3wt.util.A3Collections.copyElementsTo;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3FramedImage implements A3FramedImage {

    protected final List<Frame> frames = new ArrayList<>();
    protected volatile int index = 0;
    protected volatile boolean disposed = false;
    protected volatile int loops = 0;

    public DefaultA3FramedImage(final Collection<Frame> frames) {
        for (Frame frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedImage(final Frame... frames) {
        for (Frame frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedImage(final Iterator<Frame> frames) {
        Frame frame;
        while (frames.hasNext()) {
            frame = frames.next();
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }

    @Override
    public int getType() {
        checkDisposed("Can't call getType() on a disposed A3FramedImage");
        return get().getImage().getType();
    }

    @Override
    public int getIndex() {
        checkDisposed("Can't call getIndex() on a disposed A3FramedImage");
        return index;
    }

    @Override
    public A3FramedImage setIndex(final int index) {
        checkDisposed("Can't call setIndex() on a disposed A3FramedImage");
        this.index = index;
        return this;
    }

    @Override
    public int getLooping() {
        return loops;
    }

    @Override
    public A3FramedImage setLooping(final int loops) {
        this.loops = loops;
        return this;
    }

    @Override
    public int getGeneralWidth() {
        int result = 0;
        for (final Frame frame : frames) {
            result = Math.max(result, frame.getImage().getWidth());
        }
        return result;
    }

    @Override
    public int getGeneralHeight() {
        int result = 0;
        for (final Frame frame : frames) {
            result = Math.max(result, frame.getImage().getHeight());
        }
        return result;
    }

    @Override
    public Frame get() {
        checkDisposed("Can't call get() on a disposed A3FramedImage");
        return get(index);
    }

    @Override
    public A3Graphics createGraphics() {
        checkDisposed("Can't call createGraphics() on a disposed A3FramedImage");
        return get().getImage().createGraphics();
    }

    @Override
    public int getWidth() {
        checkDisposed("Can't call getWidth() on a disposed A3FramedImage");
        return get().getImage().getWidth();
    }

    @Override
    public int getHeight() {
        checkDisposed("Can't call getHeight() on a disposed A3FramedImage");
        return get().getImage().getHeight();
    }

    @Override
    public int getPixel(final int x, final int y) {
        checkDisposed("Can't call getPixel() on a disposed A3FramedImage");
        return get().getImage().getPixel(x, y);
    }

    @Override
    public A3FramedImage setPixel(final int x, final int y, final int color) {
        checkDisposed("Can't call setPixel() on a disposed A3FramedImage");
        get().getImage().setPixel(x, y, color);
        return this;
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkDisposed("Can't call getPixels() on a disposed A3FramedImage");
        get().getImage().getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public A3FramedImage setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkDisposed("Can't call setPixels() on a disposed A3FramedImage");
        get().getImage().setPixels(pixels, offset, stride, x, y, width, height);
        return this;
    }

    @Override
    public A3FramedImage copy() {
        checkDisposed("Can't call copy() on a disposed A3FramedImage");
        final DefaultA3FramedImage copy = new DefaultA3FramedImage();
        to(copy);
        return copy;
    }

    @Override
    public A3FramedImage copy(final int type) {
        checkDisposed("Can't call copy() on a disposed A3FramedImage");
        final DefaultA3FramedImage copy = new DefaultA3FramedImage();
        for (final Frame frame : frames) {
            copy.add(frame.copy(type));
        }
        return copy;
    }

    @Override
    public void to(final A3Image dst) {
        checkDisposed("Can't call set() on a disposed A3FramedImage");
        checkArgNotNull(dst, "dst");
        final List<Frame> frames = ((DefaultA3FramedImage)dst).frames;
        frames.clear();
        frames.addAll(copyElementsTo(this.frames, new ArrayList<>()));
    }

    @Override
    public void from(final A3Image src) {
        checkDisposed("Can't call from() on a disposed A3FramedImage");
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        for (final Frame frame : frames) {
            frame.dispose();
        }
        frames.clear();
    }

    @Override
    public List<Frame> filterInstance() {
        return frames;
    }

    @Override
    public int size() {
        checkDisposed("Can't call size() on a disposed A3FramedImage");
        return frames.size();
    }

    @Override
    public boolean isEmpty() {
        checkDisposed("Can't call isEmpty() on a disposed A3FramedImage");
        return frames.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        checkDisposed("Can't call contains() on a disposed A3FramedImage");
        return frames.contains(o);
    }

    @Override
    public Iterator<Frame> iterator() {
        checkDisposed("Can't call iterator() on a disposed A3FramedImage");
        return frames.iterator();
    }

    @Override
    public Object[] toArray() {
        checkDisposed("Can't call toArray() on a disposed A3FramedImage");
        return frames.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        checkDisposed("Can't call toArray() on a disposed A3FramedImage");
        return frames.toArray(a);
    }

    @Override
    public boolean add(final Frame image) {
        checkDisposed("Can't call add() on a disposed A3FramedImage");
        return frames.add(image);
    }

    @Override
    public boolean remove(final Object o) {
        checkDisposed("Can't call remove() on a disposed A3FramedImage");
        return frames.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        checkDisposed("Can't call containsAll() on a disposed A3FramedImage");
        return frames.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends Frame> c) {
        checkDisposed("Can't call addAll() on a disposed A3FramedImage");
        return frames.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends Frame> c) {
        checkDisposed("Can't call addAll() on a disposed A3FramedImage");
        return frames.addAll(index, c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        checkDisposed("Can't call removeAll() on a disposed A3FramedImage");
        return frames.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        checkDisposed("Can't call retainAll() on a disposed A3FramedImage");
        return frames.retainAll(c);
    }

    @Override
    public void clear() {
        checkDisposed("Can't call clear() on a disposed A3FramedImage");
        frames.clear();
    }

    @Override
    public Frame get(final int index) {
        checkDisposed("Can't call get() on a disposed A3FramedImage");
        return frames.get(index);
    }

    @Override
    public Frame set(final int index, final Frame element) {
        checkDisposed("Can't call set() on a disposed A3FramedImage");
        return frames.set(index, element);
    }

    @Override
    public void add(final int index, final Frame element) {
        checkDisposed("Can't call add() on a disposed A3FramedImage");
        frames.add(index, element);
    }

    @Override
    public Frame remove(final int index) {
        checkDisposed("Can't call remove() on a disposed A3FramedImage");
        return frames.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        checkDisposed("Can't call indexOf() on a disposed A3FramedImage");
        return frames.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        checkDisposed("Can't call lastIndexOf() on a disposed A3FramedImage");
        return frames.lastIndexOf(o);
    }

    @Override
    public ListIterator<Frame> listIterator() {
        checkDisposed("Can't call listIterator() on a disposed A3FramedImage");
        return frames.listIterator();
    }

    @Override
    public ListIterator<Frame> listIterator(final int index) {
        checkDisposed("Can't call listIterator() on a disposed A3FramedImage");
        return frames.listIterator(index);
    }

    @Override
    public List<Frame> subList(final int fromIndex, final int toIndex) {
        checkDisposed("Can't call subList() on a disposed A3FramedImage");
        return frames.subList(fromIndex, toIndex);
    }

}

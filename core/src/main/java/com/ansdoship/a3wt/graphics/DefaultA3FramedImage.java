package com.ansdoship.a3wt.graphics;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Collections.deepCopy;

public class DefaultA3FramedImage implements A3FramedImage {

    protected volatile List<A3Image> frames = new ArrayList<>();
    protected volatile int index = 0;
    protected volatile boolean disposed = false;
    protected volatile int loops = 0;

    public DefaultA3FramedImage(final Collection<A3Image> frames) {
        for (A3Image frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedImage(final A3Image... frames) {
        for (A3Image frame : frames) {
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public DefaultA3FramedImage(final Iterator<A3Image> frames) {
        A3Image frame;
        while (frames.hasNext()) {
            frame = frames.next();
            checkArgNotNull(frame, "frame");
            this.frames.add(frame);
        }
    }

    public List<A3Image> getFrames() {
        return frames;
    }

    @Override
    public long getTime() {
        checkDisposed("Can't call getTime() on a disposed A3FramedImage");
        return get().getTime();
    }

    @Override
    public void setTime(final long time) {
        checkDisposed("Can't call setTime() on a disposed A3FramedImage");
        get().setTime(time);
    }

    @Override
    public int getHotSpotX() {
        checkDisposed("Can't call getHotSpotX() on a disposed A3FramedImage");
        return get().getHotSpotX();
    }

    @Override
    public void setHotSpotX(final int hotSpotX) {
        checkDisposed("Can't call setHotSpotX() on a disposed A3FramedImage");
        get().setHotSpotX(hotSpotX);
    }

    @Override
    public int getHotSpotY() {
        checkDisposed("Can't call getHotSpotY() on a disposed A3FramedImage");
        return get().getHotSpotY();
    }

    @Override
    public void setHotSpotY(final int hotSpotY) {
        checkDisposed("Can't call setHotSpotY() on a disposed A3FramedImage");
        get().setHotSpotY(hotSpotY);
    }

    @Override
    public int getIndex() {
        checkDisposed("Can't call getIndex() on a disposed A3FramedImage");
        return index;
    }

    @Override
    public void setIndex(final int index) {
        checkDisposed("Can't call setIndex() on a disposed A3FramedImage");
        this.index = index;
    }

    @Override
    public int getLooping() {
        return loops;
    }

    @Override
    public void setLooping(final int loops) {
        this.loops = loops;
    }

    @Override
    public int getGeneralWidth() {
        int result = 0;
        for (final A3Image frame : frames) {
            result = Math.max(result, frame.getWidth());
        }
        return result;
    }

    @Override
    public int getGeneralHeight() {
        int result = 0;
        for (final A3Image frame : frames) {
            result = Math.max(result, frame.getHeight());
        }
        return result;
    }

    @Override
    public A3Image get() {
        checkDisposed("Can't call get() on a disposed A3FramedImage");
        return get(index);
    }

    @Override
    public A3Graphics getGraphics() {
        checkDisposed("Can't call getGraphics() on a disposed A3FramedImage");
        return get().getGraphics();
    }

    @Override
    public int getWidth() {
        checkDisposed("Can't call getWidth() on a disposed A3FramedImage");
        return get().getWidth();
    }

    @Override
    public int getHeight() {
        checkDisposed("Can't call getHeight() on a disposed A3FramedImage");
        return get().getHeight();
    }

    @Override
    public int getPixel(final int x, final int y) {
        checkDisposed("Can't call getPixel() on a disposed A3FramedImage");
        return get().getPixel(x, y);
    }

    @Override
    public void setPixel(final int x, final int y, final int color) {
        checkDisposed("Can't call setPixel() on a disposed A3FramedImage");
        get().setPixel(x, y, color);
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkDisposed("Can't call getPixels() on a disposed A3FramedImage");
        get().getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkDisposed("Can't call setPixels() on a disposed A3FramedImage");
        get().setPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3FramedImage");
        final Collection<A3Image> frames = deepCopy(this.frames);
        return new DefaultA3FramedImage(frames);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        for (final A3Image frame : frames) {
            frame.dispose();
        }
        frames.clear();
        frames = null;
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
    public Iterator<A3Image> iterator() {
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
    public boolean add(final A3Image image) {
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
    public boolean addAll(final Collection<? extends A3Image> c) {
        checkDisposed("Can't call addAll() on a disposed A3FramedImage");
        return frames.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends A3Image> c) {
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
    public A3Image get(final int index) {
        checkDisposed("Can't call get() on a disposed A3FramedImage");
        return frames.get(index);
    }

    @Override
    public A3Image set(final int index, final A3Image element) {
        checkDisposed("Can't call set() on a disposed A3FramedImage");
        return frames.set(index, element);
    }

    @Override
    public void add(final int index, final A3Image element) {
        checkDisposed("Can't call add() on a disposed A3FramedImage");
        frames.add(index, element);
    }

    @Override
    public A3Image remove(final int index) {
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
    public ListIterator<A3Image> listIterator() {
        checkDisposed("Can't call listIterator() on a disposed A3FramedImage");
        return frames.listIterator();
    }

    @Override
    public ListIterator<A3Image> listIterator(final int index) {
        checkDisposed("Can't call listIterator() on a disposed A3FramedImage");
        return frames.listIterator(index);
    }

    @Override
    public List<A3Image> subList(final int fromIndex, final int toIndex) {
        checkDisposed("Can't call subList() on a disposed A3FramedImage");
        return frames.subList(fromIndex, toIndex);
    }

}

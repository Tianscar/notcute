package io.notcute.g2d;

import io.notcute.util.AlreadyDisposedException;
import io.notcute.util.SwapCloneable;
import io.notcute.util.collections.FilterList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public class AnimatedImage extends FilterList<Image.Frame> implements RandomAccess, Image, SwapCloneable {
    
    private volatile int index = 0;
    private volatile boolean disposed = false;
    private volatile int loops = 0;

    public AnimatedImage(Collection<Frame> frames) {
        super(new ArrayList<>(frames.size()));
        list.addAll(frames);
    }

    public AnimatedImage(Frame... frames) {
        super(new ArrayList<>(frames.length));
        list.addAll(Arrays.asList(frames));
    }

    public void setFrames(Collection<Frame> frames) {
        list.clear();
        list.addAll(frames);
    }

    public void setFrames(Frame... frames) {
        list.clear();
        list.addAll(Arrays.asList(frames));
    }

    @Override
    public int getType() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get().getImage().getType();
    }

    public int getIndex() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return index;
    }
    
    public void setIndex(int index) {
        if (isDisposed()) throw new AlreadyDisposedException();
        this.index = index;
    }
    
    public int getLooping() {
        return loops;
    }
    
    public void setLooping(int loops) {
        this.loops = loops;
    }
    
    public int getGeneralWidth() {
        int result = 0;
        for (Frame frame : list) {
            result = Math.max(result, frame.getImage().getWidth());
        }
        return result;
    }
    
    public int getGeneralHeight() {
        int result = 0;
        for (Frame frame : list) {
            result = Math.max(result, frame.getImage().getHeight());
        }
        return result;
    }
    
    public int getGeneralType() {
        int result = Integer.MAX_VALUE;
        for (Frame frame : list) {
            result = Math.min(result, frame.getImage().getType());
        }
        return result;
    }
    
    public Frame get() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get(index);
    }

    @Override
    public Graphics getGraphics() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get().getImage().getGraphics();
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get().getImage().getWidth();
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get().getImage().getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return get().getImage().getPixel(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if (isDisposed()) throw new AlreadyDisposedException();
        get().getImage().setPixel(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        get().getImage().getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        get().getImage().setPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public AnimatedImage clone() {
        if (isDisposed()) throw new AlreadyDisposedException();
        try {
            AnimatedImage image = (AnimatedImage) super.clone();
            image.setFrames(this);
            return image;
        } catch (CloneNotSupportedException e) {
            return new AnimatedImage(this);
        }
    }

    @Override
    public void to(Object dst) {
        AnimatedImage image = (AnimatedImage) dst;
        image.setFrames(this);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        for (Frame frame : list) {
            frame.dispose();
        }
        list.clear();
    }

}

package a3wt.android;

import a3wt.graphics.A3Dimension;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Dimension implements A3Dimension {

    protected int width, height;

    public AndroidA3Dimension() {
        reset();
    }

    public AndroidA3Dimension(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public A3Dimension setWidth(final int width) {
        this.width = width;
        return this;
    }

    @Override
    public A3Dimension setHeight(final int height) {
        this.height = height;
        return this;
    }

    @Override
    public A3Dimension set(final int width, final int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Dimension copy() {
        return new AndroidA3Dimension(width, height);
    }

    @Override
    public void to(final A3Dimension dst) {
        checkArgNotNull(dst, "dst");
        dst.set(width, height);
    }

    @Override
    public void from(final A3Dimension src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Dimension reset() {
        width = height = 0;
        return this;
    }

}

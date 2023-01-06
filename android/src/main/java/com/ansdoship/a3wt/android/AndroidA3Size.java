package com.ansdoship.a3wt.android;

import com.ansdoship.a3wt.graphics.A3Size;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Size implements A3Size {

    protected float width, height;

    public AndroidA3Size() {
        reset();
    }

    public AndroidA3Size(final float width, final float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public A3Size setWidth(final float width) {
        this.width = width;
        return this;
    }

    @Override
    public A3Size setHeight(final float height) {
        this.height = height;
        return this;
    }

    @Override
    public A3Size set(final float width, final float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public A3Size copy() {
        return new AndroidA3Size(width, height);
    }

    @Override
    public void to(final A3Size dst) {
        checkArgNotNull(dst, "dst");
        dst.set(width, height);
    }

    @Override
    public void from(final A3Size src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Size reset() {
        width = height = 0;
        return this;
    }

}

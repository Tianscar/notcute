package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Size;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Size implements A3Size {

    protected final Dimension2D.Float dimension2D;

    public AWTA3Size(final Dimension2D.Float dimension2D) {
        checkArgNotNull(dimension2D, "dimension2D");
        this.dimension2D = dimension2D;
    }

    public Dimension2D getDimension2D() {
        return dimension2D;
    }

    @Override
    public float getWidth() {
        return dimension2D.width;
    }

    @Override
    public float getHeight() {
        return dimension2D.height;
    }

    @Override
    public void setWidth(final float width) {
        dimension2D.width = width;
    }

    @Override
    public void setHeight(final float height) {
        dimension2D.height = height;
    }

    @Override
    public void setSize(final float width, final float height) {
        dimension2D.setSize(width, height);
    }

    @Override
    public A3Size copy() {
        return new AWTA3Size((Dimension2D.Float) dimension2D.clone());
    }

    @Override
    public void to(final A3Size dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Size)dst).dimension2D.setSize(dimension2D);
    }

    @Override
    public void from(final A3Size src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}

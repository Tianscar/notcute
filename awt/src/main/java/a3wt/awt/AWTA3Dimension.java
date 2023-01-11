package a3wt.awt;

import a3wt.graphics.A3Dimension;

import java.awt.Dimension;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Dimension implements A3Dimension {

    protected final Dimension dimension;

    public AWTA3Dimension(final Dimension dimension) {
        checkArgNotNull(dimension, "dimension");
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public int getWidth() {
        return dimension.width;
    }

    @Override
    public int getHeight() {
        return dimension.height;
    }

    @Override
    public A3Dimension setWidth(final int width) {
        dimension.width = width;
        return this;
    }

    @Override
    public A3Dimension setHeight(final int height) {
        dimension.height = height;
        return this;
    }

    @Override
    public A3Dimension set(final int width, final int height) {
        dimension.setSize(width, height);
        return this;
    }

    @Override
    public A3Dimension copy() {
        return new AWTA3Dimension((Dimension) dimension.clone());
    }

    @Override
    public void to(final A3Dimension dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Dimension)dst).dimension.setSize(dimension);
    }

    @Override
    public void from(final A3Dimension src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Dimension reset() {
        dimension.setSize(0, 0);
        return this;
    }

}

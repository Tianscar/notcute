package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Transform;

import java.awt.geom.AffineTransform;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Transform implements A3Transform {

    protected final AffineTransform affineTransform;

    public static final AffineTransform DEFAULT_TRANSFORM = new AffineTransform();

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public AWTA3Transform(final AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }

    @Override
    public void postTranslate(final float dx, final float dy) {
        affineTransform.translate(dx, dy);
    }

    @Override
    public void postScale(final float sx, final float sy) {
        affineTransform.scale(sx, sy);
    }

    @Override
    public void postScale(final float sx, final float sy, final float px, final float py) {
        affineTransform.translate(px, py);
        affineTransform.scale(sx, sy);
        affineTransform.translate(-px, -py);
    }

    @Override
    public void postSkew(final float kx, final float ky) {
        affineTransform.shear(kx, ky);
    }

    @Override
    public void postSkew(final float kx, final float ky, final float px, final float py) {
        affineTransform.translate(px, py);
        affineTransform.shear(kx, ky);
        affineTransform.translate(-px, -py);
    }

    @Override
    public void postRotate(final float degrees) {
        affineTransform.rotate(degrees);
    }

    @Override
    public void postRotate(final float degrees, final float px, final float py) {
        affineTransform.rotate(degrees, px, py);
    }

    @Override
    public void postConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        affineTransform.concatenate(((AWTA3Transform)other).affineTransform);
    }

    @Override
    public void preTranslate(final float dx, final float dy) {
        affineTransform.preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
    }

    @Override
    public void preScale(final float sx, final float sy) {
        affineTransform.preConcatenate(AffineTransform.getScaleInstance(sx, sy));
    }

    @Override
    public void preScale(final float sx, final float sy, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.scale(sx, sy);
        transform.translate(-px, -py);
        affineTransform.preConcatenate(transform);
    }

    @Override
    public void preSkew(final float kx, final float ky) {
        affineTransform.preConcatenate(AffineTransform.getShearInstance(kx, ky));
    }

    @Override
    public void preSkew(final float kx, final float ky, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.shear(kx, ky);
        transform.translate(-px, -py);
        affineTransform.preConcatenate(transform);
    }

    @Override
    public void preRotate(final float degrees) {
        affineTransform.preConcatenate(AffineTransform.getRotateInstance(degrees));
    }

    @Override
    public void preRotate(final float degrees, final float px, final float py) {
        affineTransform.preConcatenate(AffineTransform.getRotateInstance(degrees, px, py));
    }

    @Override
    public void preConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        affineTransform.preConcatenate(((AWTA3Transform)other).affineTransform);
    }

    @Override
    public void setTranslate(final float dx, final float dy) {
        affineTransform.setToTranslation(dx, dy);
    }

    @Override
    public void setScale(final float sx, final float sy) {
        affineTransform.setToScale(sx, sy);
    }

    @Override
    public void setScale(final float sx, final float sy, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.scale(sx, sy);
        transform.translate(-px, -py);
        affineTransform.setTransform(transform);
    }

    @Override
    public void setSkew(final float kx, final float ky) {
        affineTransform.setToShear(kx, ky);
    }

    @Override
    public void setSkew(final float kx, final float ky, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.shear(kx, ky);
        transform.translate(-px, -py);
        affineTransform.setTransform(transform);
    }

    @Override
    public void setRotate(final float degrees) {
        affineTransform.setToRotation(degrees);
    }

    @Override
    public void setRotate(final float degrees, final float px, final float py) {
        affineTransform.setToRotation(degrees, px, py);
    }

    @Override
    public void setConcat(final A3Transform... transforms) {
        checkArgNotEmpty(transforms, "transforms");
        final AffineTransform transform = new AffineTransform(((AWTA3Transform)transforms[0]).affineTransform);
        for (int i = 1; i < transforms.length; i ++) {
            transform.concatenate(((AWTA3Transform)transforms[i]).affineTransform);
        }
        affineTransform.setTransform(transform);
    }

    @Override
    public void getValues(final float[] values) {
        checkArgNotNull(values, "values");
        final double[] values0 = new double[values.length];
        affineTransform.getMatrix(values0);
        for (int i = 0; i < values0.length; i ++) {
            values[i] = (float) values0[i];
        }
    }

    @Override
    public void setValues(final float[] values) {
        checkArgNotNull(values, "values");
        affineTransform.setTransform(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    @Override
    public void reset() {
        affineTransform.setTransform(DEFAULT_TRANSFORM);
    }

    @Override
    public A3Transform copy() {
        return new AWTA3Transform((AffineTransform) affineTransform.clone());
    }

    @Override
    public void to(final A3Transform dst) {
        checkArgNotNull(dst, "dst");
        dst.from(this);
    }

    @Override
    public void from(final A3Transform src) {
        checkArgNotNull(src, "src");
        affineTransform.setTransform(((AWTA3Transform)src).affineTransform);
    }

}

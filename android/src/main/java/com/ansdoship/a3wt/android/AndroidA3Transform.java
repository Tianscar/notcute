package com.ansdoship.a3wt.android;

import android.graphics.Matrix;
import com.ansdoship.a3wt.graphics.A3Transform;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Transform implements A3Transform {

    protected final Matrix matrix;

    public Matrix getMatrix() {
        return matrix;
    }

    public AndroidA3Transform(final Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void postTranslate(final float dx, final float dy) {
        matrix.postTranslate(dx, dy);
    }

    @Override
    public void postScale(final float sx, final float sy) {
        matrix.postScale(sx, sy);
    }

    @Override
    public void postScale(final float sx, final float sy, final float px, final float py) {
        matrix.postScale(sx, sy, px, py);
    }

    @Override
    public void postSkew(final float kx, final float ky) {
        matrix.postSkew(kx, ky);
    }

    @Override
    public void postSkew(final float kx, final float ky, final float px, final float py) {
        matrix.postSkew(kx, ky, px, py);
    }

    @Override
    public void postRotate(final float degrees) {
        matrix.postRotate(degrees);
    }

    @Override
    public void postRotate(final float degrees, final float px, final float py) {
        matrix.postRotate(degrees, px, py);
    }

    @Override
    public void postConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        matrix.postConcat(((AndroidA3Transform)other).matrix);
    }

    @Override
    public void preTranslate(final float dx, final float dy) {
        matrix.preTranslate(dx, dy);
    }

    @Override
    public void preScale(final float sx, final float sy) {
        matrix.preScale(sx, sy);
    }

    @Override
    public void preScale(final float sx, final float sy, final float px, final float py) {
        matrix.preScale(sx, sy, px, py);
    }

    @Override
    public void preSkew(final float kx, final float ky) {
        matrix.preSkew(kx, ky);
    }

    @Override
    public void preSkew(final float kx, final float ky, final float px, final float py) {
        matrix.preSkew(kx, ky, px, py);
    }

    @Override
    public void preRotate(final float degrees) {
        matrix.preRotate(degrees);
    }

    @Override
    public void preRotate(final float degrees, final float px, final float py) {
        matrix.preRotate(degrees, px, py);
    }

    @Override
    public void preConcat(final A3Transform other) {
        checkArgNotNull(other, "other");
        matrix.preConcat(((AndroidA3Transform)other).matrix);
    }

    @Override
    public void setTranslate(final float dx, final float dy) {
        matrix.setTranslate(dx, dy);
    }

    @Override
    public void setScale(final float sx, final float sy) {
        matrix.setScale(sx, sy);
    }

    @Override
    public void setScale(final float sx, final float sy, final float px, final float py) {
        matrix.setScale(sx, sy, px, py);
    }

    @Override
    public void setSkew(final float kx, final float ky) {
        matrix.setSkew(kx, ky);
    }

    @Override
    public void setSkew(final float kx, final float ky, final float px, final float py) {
        matrix.setSkew(kx, ky, px, py);
    }

    @Override
    public void setRotate(final float degrees) {
        matrix.setRotate(degrees);
    }

    @Override
    public void setRotate(final float degrees, final float px, final float py) {
        matrix.setRotate(degrees, px, py);
    }

    @Override
    public void setConcat(final A3Transform... transforms) {
        checkArgNotEmpty(transforms, "transforms");
        for (int i = 1; i < transforms.length; i ++) {
            matrix.setConcat(matrix, ((AndroidA3Transform)transforms[i]).matrix);
        }
    }

    @Override
    public void getValues(final float[] values) {
        matrix.getValues(values);
    }

    @Override
    public void setValues(final float[] values) {
        matrix.setValues(values);
    }

    @Override
    public void reset() {
        matrix.reset();
    }

    @Override
    public A3Transform copy() {
        return new AndroidA3Transform(new Matrix(matrix));
    }

    @Override
    public void to(final A3Transform dst) {
        checkArgNotNull(dst, "dst");
        dst.from(this);
    }

    @Override
    public void from(final A3Transform src) {
        checkArgNotNull(src, "src");
        matrix.set(((AndroidA3Transform)src).matrix);
    }

}

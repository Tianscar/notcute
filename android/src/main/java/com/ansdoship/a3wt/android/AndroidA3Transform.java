package com.ansdoship.a3wt.android;

import android.graphics.Matrix;
import android.graphics.PointF;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Transform;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgArrayLengthMin;

public class AndroidA3Transform implements A3Transform {

    public static final int ANDROID_MATRIX_VALUES_LENGTH = 9;
    public static final int MATRIX_VALUES_LENGTH = 6;

    protected final Matrix matrix;

    public Matrix getMatrix() {
        return matrix;
    }

    public AndroidA3Transform(final Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public A3Transform postTranslate(final float dx, final float dy) {
        matrix.postTranslate(dx, dy);
        return this;
    }

    @Override
    public A3Transform postScale(final float sx, final float sy) {
        matrix.postScale(sx, sy);
        return this;
    }

    @Override
    public A3Transform postScale(final float sx, final float sy, final float px, final float py) {
        matrix.postScale(sx, sy, px, py);
        return this;
    }

    @Override
    public A3Transform postSkew(final float kx, final float ky) {
        matrix.postSkew(kx, ky);
        return this;
    }

    @Override
    public A3Transform postSkew(final float kx, final float ky, final float px, final float py) {
        matrix.postSkew(kx, ky, px, py);
        return this;
    }

    @Override
    public A3Transform postRotate(final float degrees) {
        matrix.postRotate(degrees);
        return this;
    }

    @Override
    public A3Transform postRotate(final float degrees, final float px, final float py) {
        matrix.postRotate(degrees, px, py);
        return this;
    }

    @Override
    public A3Transform postConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        matrix.postConcat(((AndroidA3Transform)other).matrix);
        return this;
    }

    @Override
    public A3Transform preTranslate(final float dx, final float dy) {
        matrix.preTranslate(dx, dy);
        return this;
    }

    @Override
    public A3Transform preScale(final float sx, final float sy) {
        matrix.preScale(sx, sy);
        return this;
    }

    @Override
    public A3Transform preScale(final float sx, final float sy, final float px, final float py) {
        matrix.preScale(sx, sy, px, py);
        return this;
    }

    @Override
    public A3Transform preSkew(final float kx, final float ky) {
        matrix.preSkew(kx, ky);
        return this;
    }

    @Override
    public A3Transform preSkew(final float kx, final float ky, final float px, final float py) {
        matrix.preSkew(kx, ky, px, py);
        return this;
    }

    @Override
    public A3Transform preRotate(final float degrees) {
        matrix.preRotate(degrees);
        return this;
    }

    @Override
    public A3Transform preRotate(final float degrees, final float px, final float py) {
        matrix.preRotate(degrees, px, py);
        return this;
    }

    @Override
    public A3Transform preConcat(final A3Transform other) {
        checkArgNotNull(other, "other");
        matrix.preConcat(((AndroidA3Transform)other).matrix);
        return this;
    }

    @Override
    public A3Transform setTranslate(final float dx, final float dy) {
        matrix.setTranslate(dx, dy);
        return this;
    }

    @Override
    public A3Transform setScale(final float sx, final float sy) {
        matrix.setScale(sx, sy);
        return this;
    }

    @Override
    public A3Transform setScale(final float sx, final float sy, final float px, final float py) {
        matrix.setScale(sx, sy, px, py);
        return this;
    }

    @Override
    public A3Transform setSkew(final float kx, final float ky) {
        matrix.setSkew(kx, ky);
        return this;
    }

    @Override
    public A3Transform setSkew(final float kx, final float ky, final float px, final float py) {
        matrix.setSkew(kx, ky, px, py);
        return this;
    }

    @Override
    public A3Transform setRotate(final float degrees) {
        matrix.setRotate(degrees);
        return this;
    }

    @Override
    public A3Transform setRotate(final float degrees, final float px, final float py) {
        matrix.setRotate(degrees, px, py);
        return this;
    }

    @Override
    public A3Transform setConcat(final A3Transform... transforms) {
        checkArgNotEmpty(transforms, "transforms");
        for (int i = 1; i < transforms.length; i ++) {
            matrix.setConcat(matrix, ((AndroidA3Transform)transforms[i]).matrix);
        }
        return this;
    }

    @Override
    public float[] getMatrixValues() {
        final float[] matrixValues = new float[MATRIX_VALUES_LENGTH];
        final float[] values = mGetValues();
        System.arraycopy(values, 0, matrixValues, 0, MATRIX_VALUES_LENGTH);
        return matrixValues;
    }

    @Override
    public void getMatrixValues(final float[] matrixValues) {
        checkArgArrayLengthMin(matrixValues, MATRIX_VALUES_LENGTH, true);
        final float[] values = mGetValues();
        System.arraycopy(values, 0, matrixValues, 0, MATRIX_VALUES_LENGTH);
    }

    private float[] mGetValues() {
        final float[] values = new float[ANDROID_MATRIX_VALUES_LENGTH];
        matrix.getValues(values);
        return values;
    }

    @Override
    public float getScaleX() {
        return mGetValues()[Matrix.MSCALE_X];
    }

    @Override
    public float getScaleY() {
        return mGetValues()[Matrix.MSCALE_Y];
    }

    @Override
    public A3Point getScale() {
        final float[] values = mGetValues();
        return new AndroidA3Point(new PointF(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]));
    }

    @Override
    public void getScale(final A3Point scale) {
        checkArgNotNull(scale, "scale");
        final float[] values = mGetValues();
        scale.set(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]);
    }

    @Override
    public float getSkewX() {
        return mGetValues()[Matrix.MSKEW_X];
    }

    @Override
    public float getSkewY() {
        return mGetValues()[Matrix.MSKEW_Y];
    }

    @Override
    public A3Point getSkew() {
        final float[] values = mGetValues();
        return new AndroidA3Point(new PointF(values[Matrix.MSKEW_X], values[Matrix.MSKEW_Y]));
    }

    @Override
    public void getSkew(final A3Point skew) {
        checkArgNotNull(skew, "skew");
        final float[] values = mGetValues();
        skew.set(values[Matrix.MSKEW_X], values[Matrix.MSKEW_Y]);
    }

    @Override
    public float getTranslateX() {
        return mGetValues()[Matrix.MTRANS_X];
    }

    @Override
    public float getTranslateY() {
        return mGetValues()[Matrix.MTRANS_Y];
    }

    @Override
    public A3Point getTranslate() {
        final float[] values = mGetValues();
        return new AndroidA3Point(new PointF(values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]));
    }

    @Override
    public void getTranslate(final A3Point translate) {
        checkArgNotNull(translate, "translate");
        final float[] values = mGetValues();
        translate.set(values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]);
    }

    @Override
    public A3Transform set(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        matrix.setValues(new float[] {sx, kx, dx, ky, sy, dy, 0, 0, 1});
        return this;
    }

    @Override
    public A3Transform setMatrixValues(final float[] matrixValues) {
        checkArgArrayLengthMin(matrixValues, MATRIX_VALUES_LENGTH, true);
        final float[] values = new float[ANDROID_MATRIX_VALUES_LENGTH];
        System.arraycopy(matrixValues, 0, values, 0, MATRIX_VALUES_LENGTH);
        values[6] = 0;
        values[7] = 0;
        values[8] = 1;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setScaleX(final float sx) {
        final float[] values = mGetValues();
        values[Matrix.MSCALE_X] = sx;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setScaleY(final float sy) {
        final float[] values = mGetValues();
        values[Matrix.MSCALE_Y] = sy;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setScale(final A3Point scale) {
        final float[] values = mGetValues();
        values[Matrix.MSCALE_X] = scale.getX();
        values[Matrix.MSCALE_Y] = scale.getY();
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setSkewX(final float kx) {
        final float[] values = mGetValues();
        values[Matrix.MSKEW_X] = kx;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setSkewY(final float ky) {
        final float[] values = mGetValues();
        values[Matrix.MSKEW_Y] = ky;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setSkew(final A3Point skew) {
        final float[] values = mGetValues();
        values[Matrix.MSKEW_X] = skew.getX();
        values[Matrix.MSKEW_Y] = skew.getY();
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setTranslateX(final float dx) {
        final float[] values = mGetValues();
        values[Matrix.MTRANS_X] = dx;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setTranslateY(final float dy) {
        final float[] values = mGetValues();
        values[Matrix.MTRANS_Y] = dy;
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform setTranslate(final A3Point translate) {
        final float[] values = mGetValues();
        values[Matrix.MTRANS_X] = translate.getX();
        values[Matrix.MTRANS_Y] = translate.getY();
        matrix.setValues(values);
        return this;
    }

    @Override
    public A3Transform set(final A3Point scale, final A3Point skew, final A3Point translate) {
        checkArgNotNull(scale, "scale");
        checkArgNotNull(skew, "skew");
        checkArgNotNull(translate, "translate");
        return set(scale.getX(), skew.getX(), translate.getX(), skew.getY(), scale.getY(), translate.getY());
    }

    @Override
    public boolean isIdentity() {
        return matrix.isIdentity();
    }

    @Override
    public A3Transform reset() {
        matrix.reset();
        return this;
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

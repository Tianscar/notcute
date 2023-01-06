package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Transform;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import static com.ansdoship.a3wt.util.A3Arrays.double2float;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgArrayLengthMin;

public class AWTA3Transform implements A3Transform {
    
    public static final int MATRIX_VALUES_LENGTH = 6;

    protected final AffineTransform affineTransform;

    public static final AffineTransform DEFAULT_TRANSFORM = new AffineTransform();

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public AWTA3Transform(final AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }

    @Override
    public A3Transform postTranslate(final float dx, final float dy) {
        affineTransform.translate(dx, dy);
        return this;
    }

    @Override
    public A3Transform postScale(final float sx, final float sy) {
        affineTransform.scale(sx, sy);
        return this;
    }

    @Override
    public A3Transform postScale(final float sx, final float sy, final float px, final float py) {
        affineTransform.translate(px, py);
        affineTransform.scale(sx, sy);
        affineTransform.translate(-px, -py);
        return this;
    }

    @Override
    public A3Transform postSkew(final float kx, final float ky) {
        affineTransform.shear(kx, ky);
        return this;
    }

    @Override
    public A3Transform postSkew(final float kx, final float ky, final float px, final float py) {
        affineTransform.translate(px, py);
        affineTransform.shear(kx, ky);
        affineTransform.translate(-px, -py);
        return this;
    }

    @Override
    public A3Transform postRotate(final float degrees) {
        affineTransform.rotate(degrees);
        return this;
    }

    @Override
    public A3Transform postRotate(final float degrees, final float px, final float py) {
        affineTransform.rotate(degrees, px, py);
        return this;
    }

    @Override
    public A3Transform postConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        affineTransform.concatenate(((AWTA3Transform)other).affineTransform);
        return this;
    }

    @Override
    public A3Transform preTranslate(final float dx, final float dy) {
        affineTransform.preConcatenate(AffineTransform.getTranslateInstance(dx, dy));
        return this;
    }

    @Override
    public A3Transform preScale(final float sx, final float sy) {
        affineTransform.preConcatenate(AffineTransform.getScaleInstance(sx, sy));
        return this;
    }

    @Override
    public A3Transform preScale(final float sx, final float sy, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.scale(sx, sy);
        transform.translate(-px, -py);
        affineTransform.preConcatenate(transform);
        return this;
    }

    @Override
    public A3Transform preSkew(final float kx, final float ky) {
        affineTransform.preConcatenate(AffineTransform.getShearInstance(kx, ky));
        return this;
    }

    @Override
    public A3Transform preSkew(final float kx, final float ky, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.shear(kx, ky);
        transform.translate(-px, -py);
        affineTransform.preConcatenate(transform);
        return this;
    }

    @Override
    public A3Transform preRotate(final float degrees) {
        affineTransform.preConcatenate(AffineTransform.getRotateInstance(degrees));
        return this;
    }

    @Override
    public A3Transform preRotate(final float degrees, final float px, final float py) {
        affineTransform.preConcatenate(AffineTransform.getRotateInstance(degrees, px, py));
        return this;
    }

    @Override
    public A3Transform preConcat(final A3Transform other) {
        checkArgNotNull(other, "transform");
        affineTransform.preConcatenate(((AWTA3Transform)other).affineTransform);
        return this;
    }

    @Override
    public A3Transform setTranslate(final float dx, final float dy) {
        affineTransform.setToTranslation(dx, dy);
        return this;
    }

    @Override
    public A3Transform setScale(final float sx, final float sy) {
        affineTransform.setToScale(sx, sy);
        return this;
    }

    @Override
    public A3Transform setScale(final float sx, final float sy, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.scale(sx, sy);
        transform.translate(-px, -py);
        affineTransform.setTransform(transform);
        return this;
    }

    @Override
    public A3Transform setSkew(final float kx, final float ky) {
        affineTransform.setToShear(kx, ky);
        return this;
    }

    @Override
    public A3Transform setSkew(final float kx, final float ky, final float px, final float py) {
        final AffineTransform transform = new AffineTransform();
        transform.translate(px, py);
        transform.shear(kx, ky);
        transform.translate(-px, -py);
        affineTransform.setTransform(transform);
        return this;
    }

    @Override
    public A3Transform setRotate(final float degrees) {
        affineTransform.setToRotation(degrees);
        return this;
    }

    @Override
    public A3Transform setRotate(final float degrees, final float px, final float py) {
        affineTransform.setToRotation(degrees, px, py);
        return this;
    }

    @Override
    public A3Transform setConcat(final A3Transform... transforms) {
        checkArgNotEmpty(transforms, "transforms");
        final AffineTransform transform = new AffineTransform(((AWTA3Transform)transforms[0]).affineTransform);
        for (int i = 1; i < transforms.length; i ++) {
            transform.concatenate(((AWTA3Transform)transforms[i]).affineTransform);
        }
        affineTransform.setTransform(transform);
        return this;
    }

    @Override
    public float[] getMatrixValues() {
        final double[] matrixValues = new double[MATRIX_VALUES_LENGTH];
        affineTransform.getMatrix(matrixValues);
        return double2float(matrixValues);
    }

    @Override
    public void getMatrixValues(final float[] matrixValues) {
        checkArgNotNull(matrixValues, "values");
        checkArgArrayLengthMin(matrixValues, MATRIX_VALUES_LENGTH, true);
        final double[] matrixValues0 = new double[MATRIX_VALUES_LENGTH];
        affineTransform.getMatrix(matrixValues0);
        for (int i = 0; i < matrixValues0.length; i ++) {
            matrixValues[i] = (float) matrixValues0[i];
        }
    }

    @Override
    public float getScaleX() {
        return (float) affineTransform.getScaleX();
    }

    @Override
    public float getScaleY() {
        return (float) affineTransform.getScaleY();
    }

    @Override
    public A3Point getScale() {
        return new AWTA3Point(new Point2D.Float((float) affineTransform.getScaleX(), (float) affineTransform.getScaleY()));
    }

    @Override
    public void getScale(final A3Point scale) {
        checkArgNotNull(scale, "scale");
        scale.set((float) affineTransform.getScaleX(), (float) affineTransform.getScaleY());
    }

    @Override
    public float getSkewX() {
        return (float) affineTransform.getShearX();
    }

    @Override
    public float getSkewY() {
        return (float) affineTransform.getShearY();
    }

    @Override
    public A3Point getSkew() {
        return new AWTA3Point(new Point2D.Float((float) affineTransform.getShearX(), (float) affineTransform.getShearY()));
    }

    @Override
    public void getSkew(final A3Point skew) {
        checkArgNotNull(skew, "skew");
        skew.set((float) affineTransform.getShearX(), (float) affineTransform.getShearY());
    }

    @Override
    public float getTranslateX() {
        return (float) affineTransform.getTranslateX();
    }

    @Override
    public float getTranslateY() {
        return (float) affineTransform.getTranslateY();
    }

    @Override
    public A3Point getTranslate() {
        return new AWTA3Point(new Point2D.Float((float) affineTransform.getTranslateX(), (float) affineTransform.getTranslateY()));
    }

    @Override
    public void getTranslate(final A3Point translate) {
        checkArgNotNull(translate, "translate");
        translate.set((float) affineTransform.getTranslateX(), (float) affineTransform.getTranslateY());
    }

    @Override
    public A3Transform set(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        affineTransform.setTransform(sx, kx, dx, ky, sy, dy);
        return this;
    }

    @Override
    public A3Transform setMatrixValues(final float[] matrixValues) {
        checkArgArrayLengthMin(matrixValues, MATRIX_VALUES_LENGTH, true);
        affineTransform.setTransform(matrixValues[0], matrixValues[1], matrixValues[2], matrixValues[3], matrixValues[4], matrixValues[5]);
        return this;
    }

    @Override
    public A3Transform setScaleX(final float sx) {
        affineTransform.setToScale(sx, affineTransform.getScaleY());
        return this;
    }

    @Override
    public A3Transform setScaleY(final float sy) {
        affineTransform.setToScale(affineTransform.getScaleX(), sy);
        return this;
    }

    @Override
    public A3Transform setScale(final A3Point scale) {
        checkArgNotNull(scale, "scale");
        affineTransform.setToTranslation(scale.getX(), scale.getY());
        return this;
    }

    @Override
    public A3Transform setSkewX(final float kx) {
        affineTransform.setToShear(kx, affineTransform.getShearY());
        return this;
    }

    @Override
    public A3Transform setSkewY(final float ky) {
        affineTransform.setToShear(affineTransform.getShearX(), ky);
        return this;
    }

    @Override
    public A3Transform setSkew(final A3Point skew) {
        checkArgNotNull(skew, "skew");
        affineTransform.setToTranslation(skew.getX(), skew.getY());
        return this;
    }

    @Override
    public A3Transform setTranslateX(final float dx) {
        affineTransform.setToTranslation(dx, affineTransform.getTranslateY());
        return this;
    }

    @Override
    public A3Transform setTranslateY(final float dy) {
        affineTransform.setToTranslation(affineTransform.getTranslateX(), dy);
        return this;
    }

    @Override
    public A3Transform setTranslate(final A3Point translate) {
        checkArgNotNull(translate, "translate");
        affineTransform.setToTranslation(translate.getX(), translate.getY());
        return this;
    }

    @Override
    public A3Transform set(final A3Point scale, final A3Point skew, final A3Point translate) {
        checkArgNotNull(scale, "scale");
        checkArgNotNull(skew, "skew");
        checkArgNotNull(translate, "translate");
        affineTransform.setTransform(scale.getX(), skew.getX(), translate.getX(), skew.getY(), scale.getY(), translate.getY());
        return this;
    }

    @Override
    public boolean isIdentity() {
        return affineTransform.isIdentity();
    }

    @Override
    public A3Transform reset() {
        affineTransform.setTransform(DEFAULT_TRANSFORM);
        return this;
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

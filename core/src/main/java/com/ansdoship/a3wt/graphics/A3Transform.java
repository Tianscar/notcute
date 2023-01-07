package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;
import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Transform extends A3Copyable<A3Transform>, A3Resetable<A3Transform>, A3ExtMapBundle.Delegate {

    A3Transform postTranslate(final float dx, final float dy);
    A3Transform postScale(final float sx, final float sy);
    A3Transform postScale(final float sx, final float sy, final float px, final float py);
    A3Transform postSkew(final float kx, final float ky);
    A3Transform postSkew(final float kx, final float ky, final float px, final float py);
    A3Transform postRotate(final float degrees);
    A3Transform postRotate(final float degrees, final float px, final float py);
    A3Transform postConcat(final A3Transform other);

    A3Transform preTranslate(final float dx, final float dy);
    A3Transform preScale(final float sx, final float sy);
    A3Transform preScale(final float sx, final float sy, final float px, final float py);
    A3Transform preSkew(final float kx, final float ky);
    A3Transform preSkew(final float kx, final float ky, final float px, final float py);
    A3Transform preRotate(final float degrees);
    A3Transform preRotate(final float degrees, final float px, final float py);
    A3Transform preConcat(final A3Transform other);

    A3Transform setTranslate(final float dx, final float dy);
    A3Transform setScale(final float sx, final float sy);
    A3Transform setScale(final float sx, final float sy, final float px, final float py);
    A3Transform setSkew(final float kx, final float ky);
    A3Transform setSkew(final float kx, final float ky, final float px, final float py);
    A3Transform setRotate(final float degrees);
    A3Transform setRotate(final float degrees, final float px, final float py) ;
    A3Transform setConcat(final A3Transform... transforms);

    float[] getMatrixValues();
    void getMatrixValues(final float[] matrixValues);
    float getScaleX();
    float getScaleY();
    A3Point getScale();
    void getScale(A3Point scale);
    float getSkewX();
    float getSkewY();
    A3Point getSkew();
    void getSkew(A3Point skew);
    float getTranslateX();
    float getTranslateY();
    A3Point getTranslate();
    void getTranslate(A3Point translate);

    A3Transform set(final float sx, final float kx, final float dx,
             final float ky, final float sy, final float dy);
    A3Transform setMatrixValues(final float[] matrixValues);
    A3Transform setScaleX(final float sx);
    A3Transform setScaleY(final float sy);
    A3Transform setScale(final A3Point scale);
    A3Transform setSkewX(final float kx);
    A3Transform setSkewY(final float ky);
    A3Transform setSkew(final A3Point skew);
    A3Transform setTranslateX(final float dx);
    A3Transform setTranslateY(final float dy);
    A3Transform setTranslate(final A3Point translate);
    A3Transform set(final A3Point scale, final A3Point skew, final A3Point translate);

    boolean isIdentity();

    String KEY_SCALE_X = "scaleX";
    String KEY_SCALE_Y = "scaleY";
    String KEY_SKEW_X = "skewX";
    String KEY_SKEW_Y = "skewY";
    String KEY_TRANSLATE_X = "translateX";
    String KEY_TRANSLATE_Y = "translateY";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_SCALE_X, getScaleX());
        saver.putFloat(KEY_SCALE_Y, getScaleY());
        saver.putFloat(KEY_SKEW_X, getSkewX());
        saver.putFloat(KEY_SKEW_Y, getSkewY());
        saver.putFloat(KEY_TRANSLATE_X, getTranslateX());
        saver.putFloat(KEY_TRANSLATE_Y, getTranslateY());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_SCALE_X, 1), restorer.getFloat(KEY_SKEW_X, 0), restorer.getFloat(KEY_TRANSLATE_X, 0),
                restorer.getFloat(KEY_SKEW_Y, 0), restorer.getFloat(KEY_SCALE_Y, 1), restorer.getFloat(KEY_TRANSLATE_Y, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3Transform.class;
    }

}

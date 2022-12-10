package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Transform extends A3Copyable<A3Transform> {

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

    void get(final float[] values);

    void set(final float[] values);

    void reset();

}

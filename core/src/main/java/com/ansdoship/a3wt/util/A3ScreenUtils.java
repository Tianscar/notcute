package com.ansdoship.a3wt.util;

public class A3ScreenUtils {

    private A3ScreenUtils(){}

    public static float px2dp(final float density, final float px) {
        return px / density;
    }

    public static float dp2px(final float density, final float dp) {
        return dp * density;
    }

    public static float px2sp(final float scaledDensity, final float px) {
        return px / scaledDensity;
    }

    public static float sp2px(final float scaledDensity, final float sp) {
        return sp * scaledDensity;
    }

}

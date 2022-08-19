package com.ansdoship.a3wt.util;

public class A3ScreenUtils {

    private A3ScreenUtils(){}

    public static float px2dp(float density, float px) {
        return px / density;
    }

    public static float dp2px(float density, float dp) {
        return dp * density;
    }

    public static float px2sp(float scaledDensity, float px) {
        return px / scaledDensity;
    }

    public static float sp2px(float scaledDensity, float sp) {
        return sp * scaledDensity;
    }

}

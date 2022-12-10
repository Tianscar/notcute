package com.ansdoship.a3wt.android;

import android.graphics.Color;
import com.ansdoship.a3wt.graphics.A3Color;

public class AndroidA3Color implements A3Color {

    protected final Color color;

    public AndroidA3Color(final Color color) {
        color.getColorSpace()
        this.color = color;
    }
}

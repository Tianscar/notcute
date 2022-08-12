package com.ansdoship.a3wt.android;

import android.content.Context;
import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.util.A3Preferences;

public class AndroidA3Context implements A3Context {

    protected volatile AndroidA3Canvas canvas;

    public AndroidA3Context(AndroidA3Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public A3Canvas getCanvas() {
        return canvas;
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return new AndroidA3Preferences(canvas.getContext().getSharedPreferences(name, Context.MODE_PRIVATE), name);
    }

    @Override
    public boolean deletePreferences(String name) {
        return A3AndroidUtils.deleteSharedPreferences(canvas.getContext(), name);
    }

}

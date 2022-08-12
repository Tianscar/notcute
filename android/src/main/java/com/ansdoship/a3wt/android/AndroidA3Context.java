package com.ansdoship.a3wt.android;

import android.content.Context;
import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Images;
import com.ansdoship.a3wt.util.A3Logger;
import com.ansdoship.a3wt.util.A3Preferences;

public class AndroidA3Context implements A3Context {

    protected volatile AndroidA3Canvas canvas;
    protected static volatile AndroidA3Logger logger;
    protected static volatile AndroidA3Images images;

    public AndroidA3Context(AndroidA3Canvas canvas) {
        this.canvas = canvas;
        if (logger == null) logger = new AndroidA3Logger();
        if (images == null) images = new AndroidA3Images();
    }

    @Override
    public A3Canvas getCanvas() {
        return canvas;
    }

    @Override
    public A3Logger getLogger() {
        return logger;
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return new AndroidA3Preferences(canvas.getContext().getSharedPreferences(name, Context.MODE_PRIVATE), name);
    }

    @Override
    public boolean deletePreferences(String name) {
        return A3AndroidUtils.deleteSharedPreferences(canvas.getContext(), name);
    }

    @Override
    public A3Images getImages() {
        return images;
    }

}

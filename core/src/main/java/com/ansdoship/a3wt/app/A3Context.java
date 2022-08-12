package com.ansdoship.a3wt.app;

import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Images;
import com.ansdoship.a3wt.util.A3Logger;
import com.ansdoship.a3wt.util.A3Preferences;

public interface A3Context {

    A3Canvas getCanvas();
    A3Logger getLogger();
    A3Preferences getPreferences(String name);
    boolean deletePreferences(String name);
    A3Images getImages();

}

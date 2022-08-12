package com.ansdoship.a3wt.android;

import com.ansdoship.a3wt.A3WT;
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.util.A3Logger;

public class AndroidA3Platform implements A3Platform {

    protected static volatile AndroidA3Logger logger = new AndroidA3Logger();
    protected static volatile AndroidA3GraphicsKit graphicsKit = new AndroidA3GraphicsKit();

    static {
        A3WT.setPlatform(new AndroidA3Platform());
    }

    @Override
    public A3Logger getLogger() {
        return logger;
    }

    @Override
    public A3GraphicsKit getGraphicsKit() {
        return graphicsKit;
    }

}

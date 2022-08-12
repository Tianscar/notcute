package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.A3WT;
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.util.A3Logger;

public class AWTA3Platform implements A3Platform {

    protected static volatile AWTA3Logger logger = new AWTA3Logger();
    protected static volatile AWTA3GraphicsKit graphicsKit = new AWTA3GraphicsKit();

    static {
        A3WT.setPlatform(new AWTA3Platform());
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

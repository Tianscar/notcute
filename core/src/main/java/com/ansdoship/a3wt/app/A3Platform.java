package com.ansdoship.a3wt.app;

import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.util.A3Logger;

public interface A3Platform {

    A3Logger getLogger();
    A3GraphicsKit getGraphicsKit();

}

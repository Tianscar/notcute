package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Canvas;

public interface AWTA3Canvas extends A3Canvas {

    String getCompanyName();
    String getAppName();

    void setCompanyName(String companyName);
    void setAppName(String appName);

}

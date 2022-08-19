package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Context;

import java.io.File;

public interface AWTA3Context extends A3Context {

    String getCompanyName();
    String getAppName();

    void setCompanyName(String companyName);
    void setAppName(String appName);

    File getPreferencesFile(String name);

    void setPPIScale(float scale);
    float getPPIScale();

}

package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Images;
import com.ansdoship.a3wt.util.A3Logger;
import com.ansdoship.a3wt.util.A3Preferences;

import java.io.File;

public class AWTA3Context implements A3Context {

    protected volatile AWTA3Canvas canvas;
    protected static volatile AWTA3Logger logger;
    protected static volatile AWTA3Images images;

    protected static volatile String basePreferencesPath;
    static {
        String osName = System.getProperty("os.name").trim().toLowerCase();
        if (osName.contains("win")) {
            if (Float.parseFloat(System.getProperty("os.version")) < 6.0) {
                basePreferencesPath = "Application Data/";
            }
            else {
                basePreferencesPath = "AppData/Roaming/";
            }
        }
        else if (osName.contains("osx") || osName.contains("mac")) {
            basePreferencesPath = "Library/Application Support/";
        }
        else if (osName.contains("linux") || osName.contains("bsd")) {
            String XDGHome = System.getenv().get("XDG_DATA_HOME");
            if (XDGHome == null) XDGHome = ".local/share/";
            if (!XDGHome.endsWith("/")) XDGHome += "/";
            basePreferencesPath = XDGHome;
        }
        else basePreferencesPath = "/";

    }

    public AWTA3Context(AWTA3Canvas canvas) {
        this.canvas = canvas;
        if (logger == null) logger = new AWTA3Logger();
        if (images == null) images = new AWTA3Images();
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
        String basePreferencesPath = AWTA3Context.basePreferencesPath;
        if (canvas.getCompanyName() != null) basePreferencesPath += canvas.getCompanyName() + "/";
        if (canvas.getAppName() != null) basePreferencesPath += canvas.getAppName();
        if (basePreferencesPath.startsWith("/")) basePreferencesPath = basePreferencesPath.substring(1);
        return new AWTA3Preferences(new File(new File(System.getProperty("user.home"), basePreferencesPath), name));
    }

    @Override
    public boolean deletePreferences(String name) {
        getPreferences(name).clear();
        String basePreferencesPath = AWTA3Context.basePreferencesPath;
        if (canvas.getCompanyName() != null) basePreferencesPath += canvas.getCompanyName() + "/";
        if (canvas.getAppName() != null) basePreferencesPath += canvas.getAppName();
        if (basePreferencesPath.startsWith("/")) basePreferencesPath = basePreferencesPath.substring(1);
        return new File(new File(System.getProperty("user.home"), basePreferencesPath), name).delete();
    }

    @Override
    public A3Images getImages() {
        return images;
    }

}

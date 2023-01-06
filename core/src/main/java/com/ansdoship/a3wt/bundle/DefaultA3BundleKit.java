package com.ansdoship.a3wt.bundle;

public class DefaultA3BundleKit implements A3BundleKit {

    protected final A3I18NBundle i18NBundle = new DefaultA3I18NBundle();

    @Override
    public A3I18NBundle getI18NBundle() {
        return i18NBundle;
    }

}

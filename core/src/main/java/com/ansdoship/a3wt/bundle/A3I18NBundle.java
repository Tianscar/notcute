package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public interface A3I18NBundle {

    boolean loadAll(final File input);
    boolean load(final Locale locale, final File input);
    boolean load(final Locale locale, final InputStream input);
    boolean load(final Locale locale, final URL input);
    boolean load(final Locale locale, final A3Assets assets, final String input);

    Properties unload(final Locale locale);

    Locale putLocaleMapping(final Locale target, final Locale replacement);
    Locale removeLocaleMapping(final Locale target);
    void clearLocaleMappings();
    Locale getMappedLocale(final Locale target);

    void setDefaultLocale(final Locale locale);
    Locale getDefaultLocale();

    String get(final Locale locale, final String key, final Object... args);
    default String get(final String key, final Object... args) {
        return get(getDefaultLocale(), key, args);
    }
    String get(final Locale locale, final String key, final Collection<Object> args);
    default String get(final String key, final Collection<Object> args) {
        return get(getDefaultLocale(), key, args);
    }
    String get(final Locale locale, final String key, final Iterator<Object> args);
    default String get(final String key, final Iterator<Object> args) {
        return get(getDefaultLocale(), key, args);
    }

}

package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public interface A3I18NBundle {

    boolean loadAll(File input) throws IOException;
    boolean loadAll(A3Assets assets, String input) throws IOException;
    void load(Locale locale, File input) throws IOException;
    void load(Locale locale, InputStream input) throws IOException;
    void load(Locale locale, URL input) throws IOException;
    void load(Locale locale, A3Assets assets, String input) throws IOException;

    Properties unload(Locale locale);

    Locale putLocaleMapping(Locale target, Locale replacement);
    Locale removeLocaleMapping(Locale target);
    void clearLocaleMappings();
    Locale getMappedLocale(Locale target);

    void setDefaultLocale(Locale locale);
    Locale getDefaultLocale();

    String get(Locale locale, String key, Object... args);
    default String get(String key, Object... args) {
        return get(getDefaultLocale(), key, args);
    }
    String get(Locale locale, String key, Collection<Object> args);
    default String get(String key, Collection<Object> args) {
        return get(getDefaultLocale(), key, args);
    }
    String get(Locale locale, String key, Iterator<Object> args);
    default String get(String key, Iterator<Object> args) {
        return get(getDefaultLocale(), key, args);
    }

}

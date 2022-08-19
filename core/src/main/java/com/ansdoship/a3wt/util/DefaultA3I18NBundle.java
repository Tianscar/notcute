package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileFilter;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Asserts.notSupportedYet;

public class DefaultA3I18NBundle implements A3I18NBundle {

    protected final Map<Locale, Properties> localeProperties = new HashMap<>();
    protected final Map<Locale, Locale> localeMappings = new HashMap<>();

    protected Locale defaultLocale = Locale.getDefault();

    @Override
    public boolean loadAll(File input) throws IOException {
        checkArgNotNull(input);
        String basename = input.getName();
        List<Locale> locales = new ArrayList<>();
        File[] files = input.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) return false;
                String name = pathname.getName();
                if (!name.endsWith(".properties")) return false;
                if (!name.startsWith(basename)) return false;
                String suffix = name.replaceFirst(basename, "");
                suffix = suffix.substring(0, suffix.length() - ".properties".length());
                switch (suffix.length()) {
                    case 0:
                        locales.add(null);
                        return true;
                    case 6:
                        if (suffix.charAt(0) != '_' || suffix.charAt(3) != '_') return false;
                        locales.add(new Locale(suffix.substring(1, 3), suffix.substring(4, 6)));
                        return true;
                    case 3:
                        if (suffix.charAt(0) != '_') return false;
                        locales.add(new Locale(suffix.replaceFirst("_", "")));
                        return true;
                    default:
                        return false;
                }
            }
        });
        if (files == null) return false;
        else {
            for (int i = 0; i < files.length; i ++) {
                load(locales.get(i), files[i]);
            }
            return true;
        }
    }

    @Override
    public boolean loadAll(A3Assets assets, String input) throws IOException {
        notSupportedYet("A3I18NBundle.loadAll(A3Assets, String)");
        return false;
    }

    @Override
    public void load(Locale locale, File input) throws IOException {
        load(locale, new FileInputStream(input));
    }

    @Override
    public void load(Locale locale, InputStream input) throws IOException {
        checkArgNotNull(locale);
        Properties buffer = new Properties();
        try (InputStreamReader reader = new InputStreamReader(input, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            buffer.load(bufferedReader);
        }
        input.close();
        Properties properties = localeProperties.get(locale);
        if (properties == null) localeProperties.put(locale, buffer);
        else properties.putAll(buffer);
    }

    @Override
    public void load(Locale locale, URL input) throws IOException {
        load(locale, input.openStream());
    }

    @Override
    public void load(Locale locale, A3Assets assets, String input) throws IOException {
        load(locale, assets.readAsset(input));
    }

    @Override
    public Properties unload(Locale locale) {
        return localeProperties.remove(locale);
    }

    @Override
    public Locale putLocaleMapping(Locale target, Locale replacement) {
        return localeMappings.put(target, replacement);
    }

    @Override
    public Locale removeLocaleMapping(Locale target) {
        return localeMappings.remove(target);
    }

    @Override
    public void clearLocaleMappings() {
        localeMappings.clear();
    }

    @Override
    public Locale getMappedLocale(Locale target) {
        Locale replacement = localeMappings.get(target);
        return replacement == null ? target : replacement;
    }

    @Override
    public void setDefaultLocale(Locale locale) {
        defaultLocale = locale == null ? Locale.getDefault() : locale;
    }

    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public String get(Locale locale, String key, Object... args) {
        checkArgNotNull(locale);
        checkArgNotNull(key);
        Locale locale0 = getMappedLocale(locale);
        if (locale0.equals(getDefaultLocale())) locale0 = null;
        Properties properties = localeProperties.get(locale0);
        if (properties == null) return null;
        else {
            String value = properties.getProperty(key);
            if (value == null) return null;
            else return String.format(value, args);
        }
    }

    @Override
    public String get(Locale locale, String key, Collection<Object> args) {
        checkArgNotNull(args);
        return get(locale, key, args.toArray(new Object[0]));
    }

    @Override
    public String get(Locale locale, String key, Iterator<Object> args) {
        checkArgNotNull(args);
        List<Object> args0 = new ArrayList<>();
        while (args.hasNext()) {
            args0.add(args.next());
        }
        return get(locale, key, args0);
    }

}

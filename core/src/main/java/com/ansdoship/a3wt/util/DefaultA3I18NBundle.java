package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Charsets.UTF_8;

public class DefaultA3I18NBundle implements A3I18NBundle {

    protected final Map<Locale, Properties> localeProperties = new ConcurrentHashMap<>();
    protected final Map<Locale, Locale> localeMappings = new ConcurrentHashMap<>();

    protected volatile Locale defaultLocale = Locale.getDefault();

    @Override
    public boolean loadAll(final File input) {
        checkArgNotNull(input, "input");
        final String basename = input.getName();
        final List<Locale> locales = new ArrayList<>();
        final File[] files = input.getParentFile().listFiles(new FileFilter() {
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
    public boolean load(final Locale locale, final File input) {
        checkArgNotNull(input, "input");
        try {
            load(locale, new FileInputStream(input));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean load(final Locale locale, final InputStream input) {
        checkArgNotNull(locale, "locale");
        checkArgNotNull(input, "input");
        Properties buffer = new Properties();
        try (InputStreamReader reader = new InputStreamReader(input, UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            buffer.load(bufferedReader);
            input.close();
        } catch (IOException e) {
            return false;
        }
        Properties properties = localeProperties.get(locale);
        if (properties == null) localeProperties.put(locale, buffer);
        else properties.putAll(buffer);
        return true;
    }

    @Override
    public boolean load(final Locale locale, final URL input) {
        checkArgNotNull(input, "input");
        try {
            load(locale, input.openStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean load(final Locale locale, final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return load(locale, assets.readAsset(input));
    }

    @Override
    public Properties unload(final Locale locale) {
        checkArgNotNull(locale, "locale");
        return localeProperties.remove(locale);
    }

    @Override
    public Locale putLocaleMapping(final Locale target, final Locale replacement) {
        checkArgNotNull(target, "target");
        checkArgNotNull(replacement, "replacement");
        return localeMappings.put(target, replacement);
    }

    @Override
    public Locale removeLocaleMapping(final Locale target) {
        checkArgNotNull(target, "target");
        return localeMappings.remove(target);
    }

    @Override
    public void clearLocaleMappings() {
        localeMappings.clear();
    }

    @Override
    public Locale getMappedLocale(final Locale target) {
        checkArgNotNull(target, "target");
        final Locale replacement = localeMappings.get(target);
        return replacement == null ? target : replacement;
    }

    @Override
    public void setDefaultLocale(final Locale locale) {
        checkArgNotNull(locale, "locale");
        defaultLocale = locale;
    }

    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public String get(final Locale locale, final String key, final Object... args) {
        checkArgNotNull(locale, "locale");
        checkArgNotNull(key, "key");
        final Properties properties = localeProperties.get(getMappedLocale(locale));
        if (properties == null) return null;
        else {
            final String value = properties.getProperty(key);
            if (value == null) return null;
            else return String.format(value, args);
        }
    }

    @Override
    public String get(final Locale locale, final String key, final Collection<Object> args) {
        checkArgNotNull(args, "args");
        return get(locale, key, args.toArray(new Object[0]));
    }

    @Override
    public String get(final Locale locale, final String key, final Iterator<Object> args) {
        checkArgNotNull(args, "args");
        final List<Object> args0 = new ArrayList<>();
        while (args.hasNext()) {
            args0.add(args.next());
        }
        return get(locale, key, args0);
    }

}

package io.notcute.util;

import io.notcute.app.Assets;

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
import java.util.Objects;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class I18NText {

    private final Map<Locale, Properties> localeProperties = new ConcurrentHashMap<>();
    private final Map<Locale, Locale> localeMappings = new ConcurrentHashMap<>();

    private volatile Locale defaultLocale = Locale.getDefault();
    
    public boolean loadAll(File input) {
        Objects.requireNonNull(input);
        String basename = input.getName();
        List<Locale> locales = new ArrayList<>();
        File[] files = input.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) return false;
                String name = pathname.getName();
                if (!(name.endsWith(".properties") || name.endsWith(".prop"))) return false;
                if (!name.startsWith(basename)) return false;
                String suffix = name.replaceFirst(basename, "");
                suffix = suffix.substring(0, suffix.length() - (name.endsWith(".properties") ? ".properties".length() : ".prop".length()));
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
    
    public boolean load(Locale locale, File input) {
        Objects.requireNonNull(input);
        try {
            load(locale, new FileInputStream(input));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    
    public boolean load(Locale locale, InputStream input) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(input);
        Properties buffer = new Properties();
        try (InputStreamReader reader = new InputStreamReader(input, Charsets.UTF_8);
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
    
    public boolean load(Locale locale, URL input) {
        Objects.requireNonNull(input);
        try {
            load(locale, input.openStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean load(Locale locale, Assets assets, String input) {
        return load(locale, assets.readAsset(input));
    }
    
    public Properties unload(Locale locale) {
        Objects.requireNonNull(locale);
        return localeProperties.remove(locale);
    }
    
    public Locale putLocaleMapping(Locale target, Locale replacement) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(replacement);
        return localeMappings.put(target, replacement);
    }
    
    public Locale removeLocaleMapping(Locale target) {
        Objects.requireNonNull(target);
        return localeMappings.remove(target);
    }
    
    public void clearLocaleMappings() {
        localeMappings.clear();
    }
    
    public Locale getMappedLocale(Locale target) {
        Objects.requireNonNull(target);
        Locale replacement = localeMappings.get(target);
        return replacement == null ? target : replacement;
    }
    
    public void setDefaultLocale(Locale locale) {
        defaultLocale = Objects.requireNonNull(locale);
    }
    
    public Locale getDefaultLocale() {
        return defaultLocale;
    }
    
    public String get(Locale locale, String key, Object... args) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(key);
        Properties properties = localeProperties.get(getMappedLocale(locale));
        if (properties == null) return null;
        else {
            String value = properties.getProperty(key);
            if (value == null) return null;
            else return String.format(value, args);
        }
    }
    
    public String get(Locale locale, String key, Collection<Object> args) {
        Objects.requireNonNull(args);
        return get(locale, key, args.toArray(new Object[0]));
    }

}

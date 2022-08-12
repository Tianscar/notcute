package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.util.A3Preferences;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.ansdoship.a3wt.util.A3FileUtils.createFileIfNotExist;

public class AWTA3Preferences implements A3Preferences {

    protected final Properties properties;
    protected final Map<String, Object> cache = new HashMap<>();
    protected final File file;

    public AWTA3Preferences(File file) {
        this.properties = new Properties();
        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                properties.loadFromXML(bufferedInputStream);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.file = file;
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<String, ?> getCache() {
        return cache;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public A3Preferences putByte(String key, byte value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putShort(String key, short value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putInt(String key, int value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putLong(String key, long value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putFloat(String key, float value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putDouble(String key, double value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putBoolean(String key, boolean value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putChar(String key, char value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putString(String key, String value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public byte getByte(String key, byte defValue) {
        return (byte) properties.getOrDefault(key, defValue);
    }

    @Override
    public short getShort(String key, short defValue) {
        return (short) properties.getOrDefault(key, defValue);
    }

    @Override
    public int getInt(String key, int defValue) {
        return (int) properties.getOrDefault(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return (long) properties.getOrDefault(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return (float) properties.getOrDefault(key, defValue);
    }

    @Override
    public double getDouble(String key, double defValue) {
        return (double) properties.getOrDefault(key, defValue);
    }

    @Override
    public char getChar(String key, char defValue) {
        return (char) properties.getOrDefault(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return (String) properties.getOrDefault(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return properties.containsKey(key);
    }

    @Override
    public A3Preferences remove(String key) {
        cache.remove(key);
        return this;
    }

    @Override
    public A3Preferences clear() {
        cache.clear();
        return this;
    }

    @Override
    public boolean commit() {
        flush();
        return write();
    }

    @Override
    public void apply() {
        flush();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                write();
            }
        });
    }

    protected void flush() {
        properties.putAll(cache);
    }

    protected boolean write() {
        if (createFileIfNotExist(file)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                properties.storeToXML(bufferedOutputStream, null, "UTF-8");
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        else return false;
    }

}

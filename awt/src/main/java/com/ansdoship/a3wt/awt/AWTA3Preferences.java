package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Preferences;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.createFileIfNotExist;
import static com.ansdoship.a3wt.util.A3Files.copyTo;

public class AWTA3Preferences implements A3Preferences {

    protected final Properties properties;
    protected final Map<String, Object> cache = new ConcurrentHashMap<>();
    protected final File file;
    protected final File bakFile;
    protected final ReentrantLock fileLock = new ReentrantLock();

    public AWTA3Preferences(final File file) {
        checkArgNotNull(file, "file");
        this.properties = new Properties();
        this.file = file;
        this.bakFile = new File(file.getAbsolutePath() + ".bak");
        fileLock.lock();
        try {
            if (bakFile.exists() && bakFile.isFile()) {
                copyTo(bakFile, file);
                bakFile.delete();
            }
            if (file.exists() && file.isFile()) {
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                    properties.loadFromXML(bufferedInputStream);
                }
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        finally {
            fileLock.unlock();
        }
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

    protected A3Preferences put(final String key, final String value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        cache.put(key, value);
        return this;
    }

    @Override
    public A3Preferences putByte(final String key, final byte value) {
        return put(key, Byte.toString(value));
    }

    @Override
    public A3Preferences putShort(final String key, final short value) {
        return put(key, Short.toString(value));
    }

    @Override
    public A3Preferences putInt(final String key, final int value) {
        return put(key, Integer.toString(value));
    }

    @Override
    public A3Preferences putLong(final String key, final long value) {
        return put(key, Long.toString(value));
    }

    @Override
    public A3Preferences putFloat(final String key, final float value) {
        return put(key, Float.toString(value));
    }

    @Override
    public A3Preferences putDouble(final String key, final double value) {
        return put(key, Double.toString(value));
    }

    @Override
    public A3Preferences putBoolean(final String key, final boolean value) {
        return put(key, Boolean.toString(value));
    }

    @Override
    public A3Preferences putChar(final String key, final char value) {
        return put(key, Character.toString(value));
    }

    @Override
    public A3Preferences putString(final String key, final String value) {
        return put(key, value);
    }

    @Override
    public A3Preferences putBigInteger(final String key, final BigInteger value) {
        return put(key, value.toString());
    }

    @Override
    public A3Preferences putBigDecimal(final String key, final BigDecimal value) {
        return put(key, value.toPlainString());
    }

    protected String get(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        return (String) properties.getOrDefault(key, defValue);
    }

    @Override
    public byte getByte(final String key, final byte defValue) {
        return Byte.parseByte(get(key, Byte.toString(defValue)));
    }

    @Override
    public short getShort(final String key, final short defValue) {
        return Short.parseShort(get(key, Short.toString(defValue)));
    }

    @Override
    public int getInt(final String key, final int defValue) {
        return Integer.parseInt(get(key, Integer.toString(defValue)));
    }

    @Override
    public long getLong(final String key, final long defValue) {
        return Long.parseLong(get(key, Long.toString(defValue)));
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        return Float.parseFloat(get(key, Float.toString(defValue)));
    }

    @Override
    public double getDouble(final String key, final double defValue) {
        return Double.parseDouble(get(key, Double.toString(defValue)));
    }

    @Override
    public char getChar(final String key, final char defValue) {
        return get(key, Character.toString(defValue)).charAt(0);
    }

    @Override
    public String getString(final String key, final String defValue) {
        return get(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(final String key, final BigInteger defValue) {
        return new BigInteger(get(key, defValue.toString()));
    }

    @Override
    public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
        return new BigDecimal(get(key, defValue.toPlainString()));
    }

    @Override
    public boolean contains(final String key) {
        checkArgNotNull(key, "key");
        return properties.contains(key);
    }

    @Override
    public A3Preferences remove(final String key) {
        checkArgNotNull(key, "key");
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
        cache.clear();
    }

    protected boolean write() {
        fileLock.lock();
        try {
            if (file.exists()) {
                if (createFileIfNotExist(bakFile)) copyTo(file, bakFile);
                else return false;
            }
            if (createFileIfNotExist(file)) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                    properties.storeToXML(bufferedOutputStream, null, "UTF-8");
                    bakFile.delete();
                    return true;
                }
            }
            bakFile.delete();
            return false;
        }
        catch (final IOException e) {
            return false;
        }
        finally {
            fileLock.unlock();
        }
    }

}

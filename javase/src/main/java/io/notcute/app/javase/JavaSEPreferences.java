package io.notcute.app.javase;

import io.notcute.app.Preferences;
import io.notcute.util.signalslot.Connection;
import io.notcute.util.signalslot.VoidSignal0;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class JavaSEPreferences implements Preferences {

    private final Properties properties;
    private final Map<String, Object> cache;
    private final File file;
    private final File bakFile;
    private final ReentrantLock fileLock;
    private final VoidSignal0 onApply;

    public JavaSEPreferences(File file) {
        Objects.requireNonNull(file);
        onApply = new VoidSignal0();
        onApply.connect(this::write, Connection.Type.QUEUED);
        this.properties = new Properties();
        this.cache = new ConcurrentHashMap<>();
        this.file = file;
        this.bakFile = new File(file.getAbsolutePath() + ".bak");
        this.fileLock = new ReentrantLock();
        fileLock.lock();
        try {
            if (bakFile.exists() && bakFile.isFile()) {
                Files.copy(bakFile.toPath(), file.toPath());
                bakFile.delete();
            }
            if (file.exists() && file.isFile()) {
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                    properties.loadFromXML(bufferedInputStream);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
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

    private Preferences put(String key, String value) {
        cache.put(key, value);
        return this;
    }

    @Override
    public Preferences putByte(String key, byte value) {
        return put(key, Byte.toString(value));
    }

    @Override
    public Preferences putShort(String key, short value) {
        return put(key, Short.toString(value));
    }

    @Override
    public Preferences putInt(String key, int value) {
        return put(key, Integer.toString(value));
    }

    @Override
    public Preferences putLong(String key, long value) {
        return put(key, Long.toString(value));
    }

    @Override
    public Preferences putFloat(String key, float value) {
        return put(key, Float.toString(value));
    }

    @Override
    public Preferences putDouble(String key, double value) {
        return put(key, Double.toString(value));
    }

    @Override
    public Preferences putBoolean(String key, boolean value) {
        return put(key, Boolean.toString(value));
    }

    @Override
    public Preferences putChar(String key, char value) {
        return put(key, Character.toString(value));
    }

    @Override
    public Preferences putString(String key, String value) {
        if (value == null) return remove(key);
        else return put(key, value);
    }

    @Override
    public Preferences putBigInteger(String key, BigInteger value) {
        Objects.requireNonNull(value);
        return put(key, value.toString());
    }

    @Override
    public Preferences putBigDecimal(String key, BigDecimal value) {
        Objects.requireNonNull(value);
        return put(key, value.toPlainString());
    }

    protected String get(String key, String defValue) {
        Objects.requireNonNull(key);
        return (String) properties.getOrDefault(key, defValue);
    }

    @Override
    public byte getByte(String key, byte defValue) {
        String value = get(key, null);
        return value == null ? defValue : Byte.parseByte(value);
    }

    @Override
    public short getShort(String key, short defValue) {
        String value = get(key, null);
        return value == null ? defValue : Short.parseShort(value);
    }

    @Override
    public int getInt(String key, int defValue) {
        String value = get(key, null);
        return value == null ? defValue : Integer.parseInt(value);
    }

    @Override
    public long getLong(String key, long defValue) {
        String value = get(key, null);
        return value == null ? defValue : Long.parseLong(value);
    }

    @Override
    public float getFloat(String key, float defValue) {
        String value = get(key, null);
        return value == null ? defValue : Float.parseFloat(value);
    }

    @Override
    public double getDouble(String key, double defValue) {
        String value = get(key, null);
        return value == null ? defValue : Double.parseDouble(value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        String value = get(key, null);
        return value == null ? defValue : Boolean.parseBoolean(value);
    }

    @Override
    public char getChar(String key, char defValue) {
        String value = get(key, null);
        return value == null ? defValue : value.charAt(0);
    }

    @Override
    public String getString(String key, String defValue) {
        return get(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defValue) {
        String value = get(key, null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        String value = get(key, null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public boolean contains(String key) {
        return properties.contains(key);
    }

    @Override
    public Preferences remove(String key) {
        cache.remove(key);
        return this;
    }

    @Override
    public Preferences clear() {
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
        onApply.emit();
    }

    private void flush() {
        properties.putAll(cache);
        cache.clear();
    }

    private boolean write() {
        fileLock.lock();
        try {
            if (file.exists()) {
                if (createFileIfNotExist(bakFile)) Files.copy(file.toPath(), bakFile.toPath());
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
        catch (IOException e) {
            return false;
        }
        finally {
            fileLock.unlock();
        }
    }

    private static boolean createFileIfNotExist(final File file) {
        try {
            if (file.exists() && file.isFile()) return true;
            else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) return false;
                }
                return file.createNewFile();
            }
        }
        catch (IOException e) {
            return false;
        }
    }

}

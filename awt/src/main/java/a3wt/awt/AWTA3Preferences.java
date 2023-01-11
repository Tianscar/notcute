package a3wt.awt;

import a3wt.app.A3Preferences;
import a3wt.util.A3Arrays;
import a3wt.util.A3TextUtils;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Files.createFileIfNotExist;
import static a3wt.util.A3Files.copyTo;

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
                catch (final IOException e) {
                    e.printStackTrace();
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
        return put(key, value == null ? "null" : value);
    }

    @Override
    public A3Preferences putBigInteger(final String key, final BigInteger value) {
        checkArgNotNull(value, "value");
        return put(key, value.toString());
    }

    @Override
    public A3Preferences putBigDecimal(final String key, final BigDecimal value) {
        checkArgNotNull(value, "value");
        return put(key, value.toPlainString());
    }

    @Override
    public A3Preferences putByteArray(final String key, final byte[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putShortArray(final String key, final short[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putIntArray(final String key, final int[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putLongArray(final String key, final long[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putFloatArray(final String key, final float[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putDoubleArray(final String key, final double[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putBooleanArray(final String key, final boolean[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putCharArray(final String key, final char[] value) {
        checkArgNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putStringArray(final String key, final String[] value) {
        checkArgElementsNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putBigIntegerArray(final String key, final BigInteger[] value) {
        checkArgElementsNotNull(value, "value");
        return put(key, Arrays.toString(value));
    }

    @Override
    public A3Preferences putBigDecimalArray(final String key, final BigDecimal[] value) {
        checkArgElementsNotNull(value, "value");
        return put(key, A3Arrays.toPlainString(value));
    }

    protected String get(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        return (String) properties.getOrDefault(key, defValue);
    }

    @Override
    public byte getByte(final String key, final byte defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Byte.parseByte(value);
    }

    @Override
    public short getShort(final String key, final short defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Short.parseShort(value);
    }

    @Override
    public int getInt(final String key, final int defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Integer.parseInt(value);
    }

    @Override
    public long getLong(final String key, final long defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Long.parseLong(value);
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Float.parseFloat(value);
    }

    @Override
    public double getDouble(final String key, final double defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Double.parseDouble(value);
    }

    @Override
    public boolean getBoolean(final String key, final boolean defValue) {
        final String value = get(key, null);
        return value == null ? defValue : Boolean.parseBoolean(value);
    }

    @Override
    public char getChar(final String key, final char defValue) {
        final String value = get(key, null);
        return value == null ? defValue : value.charAt(0);
    }

    @Override
    public String getString(final String key, final String defValue) {
        return get(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(final String key, final BigInteger defValue) {
        final String value = get(key, null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
        final String value = get(key, null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public byte[] getByteArray(final String key, final byte[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseByteArray(value);
    }

    @Override
    public short[] getShortArray(final String key, final short[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseShortArray(value);
    }

    @Override
    public int[] getIntArray(final String key, final int[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseIntArray(value);
    }

    @Override
    public long[] getLongArray(final String key, final long[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseLongArray(value);
    }

    @Override
    public float[] getFloatArray(final String key, final float[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseFloatArray(value);
    }

    @Override
    public double[] getDoubleArray(final String key, final double[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseDoubleArray(value);
    }

    @Override
    public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseBooleanArray(value);
    }

    @Override
    public char[] getCharArray(final String key, final char[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseCharArray(value);
    }

    @Override
    public String[] getStringArray(final String key, final String[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseStringArray(value);
    }

    @Override
    public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseBigIntegerArray(value);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
        final String value = get(key, null);
        return value == null ? defValue : A3TextUtils.parseBigDecimalArray(value);
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

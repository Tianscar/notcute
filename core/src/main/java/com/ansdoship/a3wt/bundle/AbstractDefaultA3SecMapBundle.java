package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.util.A3Maps;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3SecMapBundle implements A3SecMapBundle {

    protected final Map<String, A3MapBundle> map;
    protected String key = null;

    protected AbstractDefaultA3SecMapBundle(final Map<String, A3MapBundle> map) {
        checkArgNotNull(map, "map");
        this.map = map;
    }

    protected abstract A3MapBundle createMapBundle();

    @Override
    public boolean save(File output, int format) {
        return false;
    }

    @Override
    public boolean save(final OutputStream output, final int format) {
        checkArgNotNull(output, "output");
        switch (format) {
            case Format.INI:
                final Ini ini = new Ini();
                Profile.Section section;
                for (final String key : map.keySet()) {
                    section = ini.get(key);
                    section.putAll(map.get(key));
                }
                try {
                    ini.store(output);
                    return true;
                }
                catch (final IOException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean restore(final File input) {
        checkArgNotNull(input, "input");
        try (final FileInputStream fileInputStream = new FileInputStream(input); final BufferedInputStream stream = new BufferedInputStream(fileInputStream)) {
            return restore(stream);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            final Ini ini = new Ini(input);
            map.clear();
            A3MapBundle mapBundle;
            for (final String key : ini.keySet()) {
                mapBundle = createMapBundle();
                mapBundle.putAll(ini.get(key));
                map.put(key, mapBundle);
            }
            return true;
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(URL input) {
        return false;
    }

    @Override
    public boolean restore(A3Assets assets, String input) {
        return false;
    }

    @Override
    public String[] getBundleReaderFormatNames() {
        return new String[0];
    }

    @Override
    public String[] getBundleWriterFormatNames() {
        return new String[0];
    }

    @Override
    public A3SecMapBundle putByte(String key, byte value) {
        return null;
    }

    @Override
    public A3SecMapBundle putShort(String key, short value) {
        return null;
    }

    @Override
    public A3SecMapBundle putInt(String key, int value) {
        return null;
    }

    @Override
    public A3SecMapBundle putLong(String key, long value) {
        return null;
    }

    @Override
    public A3SecMapBundle putFloat(String key, float value) {
        return null;
    }

    @Override
    public A3SecMapBundle putDouble(String key, double value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public A3SecMapBundle putChar(String key, char value) {
        return null;
    }

    @Override
    public A3SecMapBundle putString(String key, String value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBigInteger(String key, BigInteger value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBigDecimal(String key, BigDecimal value) {
        return null;
    }

    @Override
    public A3SecMapBundle putByteArray(String key, byte[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putShortArray(String key, short[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putIntArray(String key, int[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putLongArray(String key, long[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putFloatArray(String key, float[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putDoubleArray(String key, double[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBooleanArray(String key, boolean[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putCharArray(String key, char[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putStringArray(String key, String[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBigIntegerArray(String key, BigInteger[] value) {
        return null;
    }

    @Override
    public A3SecMapBundle putBigDecimalArray(String key, BigDecimal[] value) {
        return null;
    }

    @Override
    public byte getByte(String key, byte defValue) {
        return 0;
    }

    @Override
    public short getShort(String key, short defValue) {
        return 0;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public double getDouble(String key, double defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public char getChar(String key, char defValue) {
        return 0;
    }

    @Override
    public String getString(String key, String defValue) {
        return null;
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defValue) {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        return null;
    }

    @Override
    public byte[] getByteArray(String key, byte[] defValue) {
        return new byte[0];
    }

    @Override
    public short[] getShortArray(String key, short[] defValue) {
        return new short[0];
    }

    @Override
    public int[] getIntArray(String key, int[] defValue) {
        return new int[0];
    }

    @Override
    public long[] getLongArray(String key, long[] defValue) {
        return new long[0];
    }

    @Override
    public float[] getFloatArray(String key, float[] defValue) {
        return new float[0];
    }

    @Override
    public double[] getDoubleArray(String key, double[] defValue) {
        return new double[0];
    }

    @Override
    public boolean[] getBooleanArray(String key, boolean[] defValue) {
        return new boolean[0];
    }

    @Override
    public char[] getCharArray(String key, char[] defValue) {
        return new char[0];
    }

    @Override
    public String[] getStringArray(String key, String[] defValue) {
        return new String[0];
    }

    @Override
    public BigInteger[] getBigIntegerArray(String key, BigInteger[] defValue) {
        return new BigInteger[0];
    }

    @Override
    public BigDecimal[] getBigDecimalArray(String key, BigDecimal[] defValue) {
        return new BigDecimal[0];
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public A3SecMapBundle remove(String key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public A3MapBundle get(Object key) {
        return null;
    }

    @Override
    public A3MapBundle put(String key, A3MapBundle value) {
        return null;
    }

    @Override
    public A3MapBundle remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends A3MapBundle> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<A3MapBundle> values() {
        return null;
    }

    @Override
    public Set<Entry<String, A3MapBundle>> entrySet() {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(final String key) {
        this.key = key;
    }

    @Override
    public A3MapBundle get(final String key, final A3MapBundle defValue) {
        return A3Maps.getOrDefault(map, key, defValue);
    }

    @Override
    public A3MapBundle get() {
        return map.get(key);
    }

}

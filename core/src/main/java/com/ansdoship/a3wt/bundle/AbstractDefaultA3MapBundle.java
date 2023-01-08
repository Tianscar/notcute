package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.util.A3Arrays;
import com.ansdoship.a3wt.util.A3Charsets;
import com.ansdoship.a3wt.util.A3Maps;
import com.ansdoship.a3wt.util.A3TextUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3MapBundle implements A3MapBundle {

    protected final Map<String, String> map;

    protected AbstractDefaultA3MapBundle(final Map<String, String> map) {
        checkArgNotNull(map, "map");
        this.map = map;
    }

    private static final String[] READER_FORMAT_NAMES = new String[] {"properties", "prop"};
    private static final String[] WRITER_FORMAT_NAMES = new String[] {"properties", "prop"};

    @Override
    public boolean save(final File output, final String format) {
        checkArgNotNull(output, "output");
        checkArgNotNull(format, "format");
        try (final FileOutputStream stream = new FileOutputStream(output)) {
            return save(stream, format);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean save(final OutputStream output, final String format) {
        checkArgNotNull(output, "output");
        checkArgNotNull(format, "format");
        switch (format.toLowerCase()) {
            case "properties":
            case "prop":
                try {
                    final Properties properties = new Properties(map.size());
                    properties.putAll(map);
                    properties.store(new OutputStreamWriter(output, A3Charsets.UTF_8), null);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean restore(final File input) {
        checkArgNotNull(input, "input");
        try (final FileInputStream fileInputStream = new FileInputStream(input);
             final BufferedInputStream stream = new BufferedInputStream(fileInputStream)) {
            return restore(stream);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final InputStream input) {
        checkArgNotNull(input, "input");
        final Properties properties = new Properties();
        try {
            properties.load(input);
        }
        catch (final IOException ex) {
            return false;
        }
        map.clear();
        final Map tmp0 = properties;
        final Map<String, String> tmp1 = (Map<String, String>) tmp0;
        map.putAll(tmp1);
        return true;
    }

    @Override
    public boolean restore(final URL input) {
        checkArgNotNull(input, "input");
        try (final InputStream stream = input.openStream()) {
            return restore(stream);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return restore(assets.readAsset(input));
    }

    @Override
    public String[] getBundleReaderFormatNames() {
        return A3Arrays.copy(READER_FORMAT_NAMES);
    }

    @Override
    public String[] getBundleWriterFormatNames() {
        return A3Arrays.copy(WRITER_FORMAT_NAMES);
    }

    private A3MapBundle put0(final String key, final String value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        map.put(key, value);
        return this;
    }

    @Override
    public A3MapBundle putByte(final String key, final byte value) {
        return put0(key, Byte.toString(value));
    }

    @Override
    public A3MapBundle putShort(final String key, final short value) {
        return put0(key, Short.toString(value));
    }

    @Override
    public A3MapBundle putInt(final String key, final int value) {
        return put0(key, Integer.toString(value));
    }

    @Override
    public A3MapBundle putLong(final String key, final long value) {
        return put0(key, Long.toString(value));
    }

    @Override
    public A3MapBundle putFloat(final String key, final float value) {
        return put0(key, Float.toString(value));
    }

    @Override
    public A3MapBundle putDouble(final String key, final double value) {
        return put0(key, Double.toString(value));
    }

    @Override
    public A3MapBundle putBoolean(final String key, final boolean value) {
        return put0(key, Boolean.toString(value));
    }

    @Override
    public A3MapBundle putChar(final String key, final char value) {
        return put0(key, Character.toString(value));
    }

    @Override
    public A3MapBundle putString(final String key, final String value) {
        return put0(key, value == null ? "null" : value);
    }

    @Override
    public A3MapBundle putBigInteger(final String key, final BigInteger value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toString());
    }

    @Override
    public A3MapBundle putBigDecimal(final String key, final BigDecimal value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toPlainString());
    }

    @Override
    public A3MapBundle putByteArray(final String key, final byte[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putShortArray(final String key, final short[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putIntArray(final String key, final int[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putLongArray(final String key, final long[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putFloatArray(final String key, final float[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putDoubleArray(final String key, final double[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putBooleanArray(final String key, final boolean[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putCharArray(final String key, final char[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putStringArray(final String key, final String[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putBigIntegerArray(final String key, final BigInteger[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3MapBundle putBigDecimalArray(final String key, final BigDecimal[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, A3Arrays.toPlainString(value));
    }

    private String get0(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        return A3Maps.getOrDefault(map, key, defValue);
    }

    @Override
    public byte getByte(final String key, final byte defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Byte.parseByte(value);
    }

    @Override
    public short getShort(final String key, final short defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Short.parseShort(value);
    }

    @Override
    public int getInt(final String key, final int defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Integer.parseInt(value);
    }

    @Override
    public long getLong(final String key, final long defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Long.parseLong(value);
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Float.parseFloat(value);
    }

    @Override
    public double getDouble(final String key, final double defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Double.parseDouble(value);
    }

    @Override
    public boolean getBoolean(final String key, final boolean defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : Boolean.parseBoolean(value);
    }

    @Override
    public char getChar(final String key, final char defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : value.charAt(0);
    }

    @Override
    public String getString(final String key, final String defValue) {
        return get0(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(final String key, final BigInteger defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public byte[] getByteArray(final String key, final byte[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseByteArray(value);
    }

    @Override
    public short[] getShortArray(final String key, final short[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseShortArray(value);
    }

    @Override
    public int[] getIntArray(final String key, final int[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseIntArray(value);
    }

    @Override
    public long[] getLongArray(final String key, final long[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseLongArray(value);
    }

    @Override
    public float[] getFloatArray(final String key, final float[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseFloatArray(value);
    }

    @Override
    public double[] getDoubleArray(final String key, final double[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseDoubleArray(value);
    }

    @Override
    public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseBooleanArray(value);
    }

    @Override
    public char[] getCharArray(final String key, final char[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseCharArray(value);
    }

    @Override
    public String[] getStringArray(final String key, final String[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseStringArray(value);
    }

    @Override
    public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseBigIntegerArray(value);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3TextUtils.parseBigDecimalArray(value);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(final Object key) {
        return map.get(key);
    }

    @Override
    public String put(final String key, final String value) {
        return map.put(key, value);
    }

    @Override
    public String remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends String> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<String> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }

}

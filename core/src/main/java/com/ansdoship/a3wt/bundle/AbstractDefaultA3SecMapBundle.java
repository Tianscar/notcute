package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.util.A3Arrays;
import com.ansdoship.a3wt.util.A3Maps;
import com.ansdoship.a3wt.util.A3StringUtils;
import org.ini4j.Ini;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3SecMapBundle implements A3SecMapBundle {

    protected final Map<String, A3MapBundle> map;
    protected String key = "";

    protected AbstractDefaultA3SecMapBundle(final Map<String, A3MapBundle> map) {
        checkArgNotNull(map, "map");
        this.map = map;
    }

    private static final String[] READER_FORMAT_NAMES = new String[] {"ini"};
    private static final String[] WRITER_FORMAT_NAMES = new String[] {"ini"};

    protected abstract A3MapBundle createMapBundle();

    @Override
    public boolean save(final File output, final int format) {
        checkArgNotNull(output, "output");
        try (final FileOutputStream stream = new FileOutputStream(output)) {
            return save(stream, format);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean save(final OutputStream output, final int format) {
        checkArgNotNull(output, "output");
        switch (format) {
            case Format.INI:
                final Ini ini = new Ini();
                A3MapBundle bundle;
                for (final String key : map.keySet()) {
                    bundle = map.get(key);
                    for (final String option : bundle.keySet()) {
                        ini.put(key, option, bundle.get(option));
                    }
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

    private A3SecMapBundle put0(final String key, final String value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        final A3MapBundle bundle = get();
        if (bundle == null) map.put(this.key, createMapBundle());
        get().put(key, value);
        return this;
    }

    @Override
    public A3SecMapBundle putByte(final String key, final byte value) {
        return put0(key, Byte.toString(value));
    }

    @Override
    public A3SecMapBundle putShort(final String key, final short value) {
        return put0(key, Short.toString(value));
    }

    @Override
    public A3SecMapBundle putInt(final String key, final int value) {
        return put0(key, Integer.toString(value));
    }

    @Override
    public A3SecMapBundle putLong(final String key, final long value) {
        return put0(key, Long.toString(value));
    }

    @Override
    public A3SecMapBundle putFloat(final String key, final float value) {
        return put0(key, Float.toString(value));
    }

    @Override
    public A3SecMapBundle putDouble(final String key, final double value) {
        return put0(key, Double.toString(value));
    }

    @Override
    public A3SecMapBundle putBoolean(final String key, final boolean value) {
        return put0(key, Boolean.toString(value));
    }

    @Override
    public A3SecMapBundle putChar(final String key, final char value) {
        return put0(key, Character.toString(value));
    }

    @Override
    public A3SecMapBundle putString(final String key, final String value) {
        return put0(key, value == null ? "null" : value);
    }

    @Override
    public A3SecMapBundle putBigInteger(final String key, final BigInteger value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toString());
    }

    @Override
    public A3SecMapBundle putBigDecimal(final String key, final BigDecimal value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toPlainString());
    }

    @Override
    public A3SecMapBundle putByteArray(final String key, final byte[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putShortArray(final String key, final short[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putIntArray(final String key, final int[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putLongArray(final String key, final long[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putFloatArray(final String key, final float[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putDoubleArray(final String key, final double[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putBooleanArray(final String key, final boolean[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putCharArray(final String key, final char[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putStringArray(final String key, final String[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putBigIntegerArray(final String key, final BigInteger[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3SecMapBundle putBigDecimalArray(final String key, final BigDecimal[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, A3Arrays.toPlainString(value));
    }

    private String get0(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        final A3MapBundle bundle = get();
        return bundle == null ? null : A3Maps.getOrDefault(bundle, key, defValue);
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
        return value == null ? defValue : A3StringUtils.parseByteArray(value);
    }

    @Override
    public short[] getShortArray(final String key, final short[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseShortArray(value);
    }

    @Override
    public int[] getIntArray(final String key, final int[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseIntArray(value);
    }

    @Override
    public long[] getLongArray(final String key, final long[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseLongArray(value);
    }

    @Override
    public float[] getFloatArray(final String key, final float[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseFloatArray(value);
    }

    @Override
    public double[] getDoubleArray(final String key, final double[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseDoubleArray(value);
    }

    @Override
    public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseBooleanArray(value);
    }

    @Override
    public char[] getCharArray(final String key, final char[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseCharArray(value);
    }

    @Override
    public String[] getStringArray(final String key, final String[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseStringArray(value);
    }

    @Override
    public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseBigIntegerArray(value);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
        final String value = get0(key, null);
        return value == null ? defValue : A3StringUtils.parseBigDecimalArray(value);
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
    public A3MapBundle get(final Object key) {
        return map.get(key);
    }

    @Override
    public A3MapBundle put(final String key, final A3MapBundle value) {
        return map.put(key, value);
    }

    @Override
    public A3MapBundle remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends A3MapBundle> m) {
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
    public Collection<A3MapBundle> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, A3MapBundle>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(final String key) {
        checkArgNotNull(key, "key");
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

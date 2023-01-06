package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.util.A3Arrays;
import com.ansdoship.a3wt.util.A3Charsets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3MapBundle implements A3MapBundle {

    protected final Map<String, String> map;

    protected AbstractDefaultA3MapBundle(final Map<String, String> map) {
        checkArgNotNull(map, "map");
        this.map = map;
    }

    private static final String[] READER_FORMAT_NAMES = new String[] {"properties", "prop", "xml"};
    private static final String[] WRITER_FORMAT_NAMES = new String[] {"properties", "prop", "xml"};

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
            case Format.PROPERTIES:
                try {
                    final Properties properties = new Properties(map.size());
                    properties.putAll(map);
                    properties.store(new OutputStreamWriter(output, A3Charsets.UTF_8), null);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            case Format.XML:
                try {
                    final Properties properties = new Properties(map.size());
                    properties.putAll(map);
                    properties.storeToXML(output, null, A3Charsets.UTF_8.name());
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
        try (final FileInputStream stream = new FileInputStream(input)) {
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
            properties.loadFromXML(input);
        }
        catch (final InvalidPropertiesFormatException e) {
            try {
                properties.load(input);
            }
            catch (final IOException ex) {
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
        map.clear();
        final Map tmp0 = properties;
        final Map<String, String> tmp1 = (Map<String, String>) tmp0;
        map.putAll(tmp1);
        return false;
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

    @Override
    public A3KeyValueBundle putByte(String key, byte value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putShort(String key, short value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putInt(String key, int value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putLong(String key, long value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putFloat(String key, float value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putDouble(String key, double value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putChar(String key, char value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putString(String key, String value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBigInteger(String key, BigInteger value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBigDecimal(String key, BigDecimal value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putByteArray(String key, byte[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putShortArray(String key, short[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putIntArray(String key, int[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putLongArray(String key, long[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putFloatArray(String key, float[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putDoubleArray(String key, double[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBooleanArray(String key, boolean[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putCharArray(String key, char[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putStringArray(String key, String[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBigIntegerArray(String key, BigInteger[] value) {
        return null;
    }

    @Override
    public A3KeyValueBundle putBigDecimalArray(String key, BigDecimal[] value) {
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
        return map.containsKey(key);
    }

    @Override
    public A3KeyValueBundle remove(String key) {
        map.remove(key);
        return this;
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
    public String get(Object key) {
        return null;
    }

    @Override
    public String put(String key, String value) {
        return null;
    }

    @Override
    public String remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {

    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return null;
    }

}

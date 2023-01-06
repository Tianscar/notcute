package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3ExtMapBundle implements A3ExtMapBundle {

    protected static class Element {
        protected final Object element;
        public Element(final A3ExtMapBundle element) {
            this.element = element;
        }
        public Object getElement() {
            return element;
        }
        public Element(final String element) {
            this.element = element;
        }
        public Element(final Delegate element) {
            this.element = element;
        }
        public boolean isExtMapBundle() {
            return element instanceof A3ExtMapBundle;
        }
        public boolean isString() {
            return element instanceof String;
        }
        public boolean isDelegate() {
            return element instanceof Delegate;
        }
    }

    protected final Map<String, Element> map;

    protected AbstractDefaultA3ExtMapBundle(final Map<String, Element> map) {
        checkArgNotNull(map, "map");
        this.map = map;
    }

    @Override
    public boolean save(File output, int format) {
        return false;
    }

    @Override
    public boolean save(OutputStream output, int format) {
        return false;
    }

    @Override
    public boolean restore(File input) {
        return false;
    }

    @Override
    public boolean restore(InputStream input) {
        return false;
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
    public A3ExtMapBundle putByte(String key, byte value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putShort(String key, short value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putInt(String key, int value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putLong(String key, long value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putFloat(String key, float value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putDouble(String key, double value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putChar(String key, char value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putString(String key, String value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBigInteger(String key, BigInteger value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBigDecimal(String key, BigDecimal value) {
        return null;
    }

    @Override
    public <T extends Delegate> A3ExtMapBundle putDelegate(String key, T value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putByteArray(String key, byte[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putShortArray(String key, short[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putIntArray(String key, int[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putLongArray(String key, long[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putFloatArray(String key, float[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putDoubleArray(String key, double[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBooleanArray(String key, boolean[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putCharArray(String key, char[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putStringArray(String key, String[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBigIntegerArray(String key, BigInteger[] value) {
        return null;
    }

    @Override
    public A3ExtMapBundle putBigDecimalArray(String key, BigDecimal[] value) {
        return null;
    }

    @Override
    public <T extends Delegate> A3ExtMapBundle putDelegateArray(String key, T[] value) {
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
    public A3ExtMapBundle get(String key, A3ExtMapBundle defValue) {
        return null;
    }

    @Override
    public <T extends Delegate> T getDelegate(String key, T defValue) {
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
    public A3ExtMapBundle[] get(String key, A3ExtMapBundle[] defValue) {
        return new A3ExtMapBundle[0];
    }

    @Override
    public <T extends Delegate> T[] getDelegateArray(String key, T[] defValue) {
        return null;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public A3ExtMapBundle remove(String key) {
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
    public A3ExtMapBundle get(Object key) {
        return null;
    }

    @Override
    public A3ExtMapBundle put(String key, A3ExtMapBundle value) {
        return null;
    }

    @Override
    public A3ExtMapBundle remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends A3ExtMapBundle> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<A3ExtMapBundle> values() {
        return null;
    }

    @Override
    public Set<Entry<String, A3ExtMapBundle>> entrySet() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public A3ExtMapBundle get() {
        return null;
    }
}

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

public class DefaultA3SectionalMapBundle implements A3SectionalMapBundle {

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
    public A3MapBundle putByte(String key, byte value) {
        return null;
    }

    @Override
    public A3MapBundle putShort(String key, short value) {
        return null;
    }

    @Override
    public A3MapBundle putInt(String key, int value) {
        return null;
    }

    @Override
    public A3MapBundle putLong(String key, long value) {
        return null;
    }

    @Override
    public A3MapBundle putFloat(String key, float value) {
        return null;
    }

    @Override
    public A3MapBundle putDouble(String key, double value) {
        return null;
    }

    @Override
    public A3MapBundle putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public A3MapBundle putChar(String key, char value) {
        return null;
    }

    @Override
    public A3MapBundle putString(String key, String value) {
        return null;
    }

    @Override
    public A3MapBundle putBigInteger(String key, BigInteger value) {
        return null;
    }

    @Override
    public A3MapBundle putBigDecimal(String key, BigDecimal value) {
        return null;
    }

    @Override
    public A3MapBundle putByteArray(String key, byte[] value) {
        return null;
    }

    @Override
    public A3MapBundle putShortArray(String key, short[] value) {
        return null;
    }

    @Override
    public A3MapBundle putIntArray(String key, int[] value) {
        return null;
    }

    @Override
    public A3MapBundle putLongArray(String key, long[] value) {
        return null;
    }

    @Override
    public A3MapBundle putFloatArray(String key, float[] value) {
        return null;
    }

    @Override
    public A3MapBundle putDoubleArray(String key, double[] value) {
        return null;
    }

    @Override
    public A3MapBundle putBooleanArray(String key, boolean[] value) {
        return null;
    }

    @Override
    public A3MapBundle putCharArray(String key, char[] value) {
        return null;
    }

    @Override
    public A3MapBundle putStringArray(String key, String[] value) {
        return null;
    }

    @Override
    public A3MapBundle putBigIntegerArray(String key, BigInteger[] value) {
        return null;
    }

    @Override
    public A3MapBundle putBigDecimalArray(String key, BigDecimal[] value) {
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
    public A3MapBundle remove(String key) {
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
        return null;
    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public A3MapBundle get(String key, A3MapBundle defValue) {
        return null;
    }

    @Override
    public A3MapBundle get() {
        return null;
    }

}

package com.ansdoship.a3wt.bundle;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface A3KeyValueBundle extends A3Bundle {

    A3KeyValueBundle putByte(final String key, final byte value);
    A3KeyValueBundle putShort(final String key, final short value);
    A3KeyValueBundle putInt(final String key, final int value);
    A3KeyValueBundle putLong(final String key, final long value);
    A3KeyValueBundle putFloat(final String key, final float value);
    A3KeyValueBundle putDouble(final String key, final double value);
    A3KeyValueBundle putBoolean(final String key, final boolean value);
    A3KeyValueBundle putChar(final String key, final char value);
    A3KeyValueBundle putString(final String key, final String value);
    A3KeyValueBundle putBigInteger(final String key, final BigInteger value);
    A3KeyValueBundle putBigDecimal(final String key, final BigDecimal value);
    A3KeyValueBundle putByteArray(final String key, final byte[] value);
    A3KeyValueBundle putShortArray(final String key, final short[] value);
    A3KeyValueBundle putIntArray(final String key, final int[] value);
    A3KeyValueBundle putLongArray(final String key, final long[] value);
    A3KeyValueBundle putFloatArray(final String key, final float[] value);
    A3KeyValueBundle putDoubleArray(final String key, final double[] value);
    A3KeyValueBundle putBooleanArray(final String key, final boolean[] value);
    A3KeyValueBundle putCharArray(final String key, final char[] value);
    A3KeyValueBundle putStringArray(final String key, final String[] value);
    A3KeyValueBundle putBigIntegerArray(final String key, final BigInteger[] value);
    A3KeyValueBundle putBigDecimalArray(final String key, final BigDecimal[] value);

    byte getByte(final String key, final byte defValue);
    short getShort(final String key, final short defValue);
    int getInt(final String key, final int defValue);
    long getLong(final String key, final long defValue);
    float getFloat(final String key, final float defValue);
    double getDouble(final String key, final double defValue);
    boolean getBoolean(final String key, final boolean defValue);
    char getChar(final String key, final char defValue);
    String getString(final String key, final String defValue);
    BigInteger getBigInteger(final String key, final BigInteger defValue);
    BigDecimal getBigDecimal(final String key, final BigDecimal defValue);
    byte[] getByteArray(final String key, final byte[] defValue);
    short[] getShortArray(final String key, final short[] defValue);
    int[] getIntArray(final String key, final int[] defValue);
    long[] getLongArray(final String key, final long[] defValue);
    float[] getFloatArray(final String key, final float[] defValue);
    double[] getDoubleArray(final String key, final double[] defValue);
    boolean[] getBooleanArray(final String key, final boolean[] defValue);
    char[] getCharArray(final String key, final char[] defValue);
    String[] getStringArray(final String key, final String[] defValue);
    BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue);
    BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue);

    boolean contains(final String key);
    A3KeyValueBundle remove(final String key);
    void clear();
    
}

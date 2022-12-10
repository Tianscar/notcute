package com.ansdoship.a3wt.bundle;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface A3MapBundle extends A3Bundle {

    A3MapBundle putByte(final String key, final byte value);
    A3MapBundle putShort(final String key, final short value);
    A3MapBundle putInt(final String key, final int value);
    A3MapBundle putLong(final String key, final long value);
    A3MapBundle putFloat(final String key, final float value);
    A3MapBundle putDouble(final String key, final double value);
    A3MapBundle putBoolean(final String key, final boolean value);
    A3MapBundle putChar(final String key, final char value);
    A3MapBundle putString(final String key, final String value);
    A3MapBundle putBigInteger(final String key, final BigInteger value);
    A3MapBundle putBigDecimal(final String key, final BigDecimal value);
    A3MapBundle putByteArray(final String key, final byte[] value);
    A3MapBundle putShortArray(final String key, final short[] value);
    A3MapBundle putIntArray(final String key, final int[] value);
    A3MapBundle putLongArray(final String key, final long[] value);
    A3MapBundle putFloatArray(final String key, final float[] value);
    A3MapBundle putDoubleArray(final String key, final double[] value);
    A3MapBundle putBooleanArray(final String key, final boolean[] value);
    A3MapBundle putCharArray(final String key, final char[] value);
    A3MapBundle putStringArray(final String key, final String[] value);
    A3MapBundle putBigIntegerArray(final String key, final BigInteger[] value);
    A3MapBundle putBigDecimalArray(final String key, final BigDecimal[] value);

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
    A3MapBundle remove(final String key);
    void clear();
    
}

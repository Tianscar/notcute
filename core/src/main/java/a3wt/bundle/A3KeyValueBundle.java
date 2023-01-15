package a3wt.bundle;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface A3KeyValueBundle<T extends A3KeyValueBundle<T>> extends A3Bundle {

    T putByte(final String key, final byte value);
    T putShort(final String key, final short value);
    T putInt(final String key, final int value);
    T putLong(final String key, final long value);
    T putFloat(final String key, final float value);
    T putDouble(final String key, final double value);
    T putBoolean(final String key, final boolean value);
    T putChar(final String key, final char value);
    T putString(final String key, final String value);
    T putBigInteger(final String key, final BigInteger value);
    T putBigDecimal(final String key, final BigDecimal value);
    T putByteArray(final String key, final byte[] value);
    T putShortArray(final String key, final short[] value);
    T putIntArray(final String key, final int[] value);
    T putLongArray(final String key, final long[] value);
    T putFloatArray(final String key, final float[] value);
    T putDoubleArray(final String key, final double[] value);
    T putBooleanArray(final String key, final boolean[] value);
    T putCharArray(final String key, final char[] value);
    T putStringArray(final String key, final String[] value);
    T putBigIntegerArray(final String key, final BigInteger[] value);
    T putBigDecimalArray(final String key, final BigDecimal[] value);

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
    
}

package a3wt.app;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface A3Preferences {

    String getName();

    A3Preferences putByte(final String key, final byte value);
    A3Preferences putShort(final String key, final short value);
    A3Preferences putInt(final String key, final int value);
    A3Preferences putLong(final String key, final long value);
    A3Preferences putFloat(final String key, final float value);
    A3Preferences putDouble(final String key, final double value);
    A3Preferences putBoolean(final String key, final boolean value);
    A3Preferences putChar(final String key, final char value);
    A3Preferences putString(final String key, final String value);
    A3Preferences putBigInteger(final String key, final BigInteger value);
    A3Preferences putBigDecimal(final String key, final BigDecimal value);
    A3Preferences putByteArray(final String key, final byte[] value);
    A3Preferences putShortArray(final String key, final short[] value);
    A3Preferences putIntArray(final String key, final int[] value);
    A3Preferences putLongArray(final String key, final long[] value);
    A3Preferences putFloatArray(final String key, final float[] value);
    A3Preferences putDoubleArray(final String key, final double[] value);
    A3Preferences putBooleanArray(final String key, final boolean[] value);
    A3Preferences putCharArray(final String key, final char[] value);
    A3Preferences putStringArray(final String key, final String[] value);
    A3Preferences putBigIntegerArray(final String key, final BigInteger[] value);
    A3Preferences putBigDecimalArray(final String key, final BigDecimal[] value);

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
    A3Preferences remove(final String key);
    A3Preferences clear();

    boolean commit();
    void apply();

}

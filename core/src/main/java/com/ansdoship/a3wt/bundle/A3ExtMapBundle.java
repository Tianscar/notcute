package com.ansdoship.a3wt.bundle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3ExtMapBundle extends A3KeyValueBundle, Map<String, A3ExtMapBundle.Value> {

    interface Value {
        Object getValue();
        Class<?> typeClass();
    }

     class DefaultValue implements Value {
        protected final Object value;
        @Override
        public Object getValue() {
            return value;
        }
        public DefaultValue(final A3ExtMapBundle value) {
            checkArgNotNull(value, "value");
            this.value = value;
        }
        public DefaultValue(final String value) {
            checkArgNotNull(value, "value");
            this.value = value;
        }
        public DefaultValue(final Delegate value) {
            checkArgNotNull(value, "value");
            this.value = value;
        }
        @Override
        public Class<?> typeClass() {
            return value.getClass();
        }
    }

    interface Delegate {
        void save(final Saver saver);
        void restore(final Restorer restorer);
        Class<? extends Delegate> typeClass();
    }

    interface Saver {
        Saver putByte(final String key, final byte value);
        Saver putShort(final String key, final short value);
        Saver putInt(final String key, final int value);
        Saver putLong(final String key, final long value);
        Saver putFloat(final String key, final float value);
        Saver putDouble(final String key, final double value);
        Saver putBoolean(final String key, final boolean value);
        Saver putChar(final String key, final char value);
        Saver putString(final String key, final String value);
        Saver putBigInteger(final String key, final BigInteger value);
        Saver putBigDecimal(final String key, final BigDecimal value);
        Saver putExtMapBundle(final String key, final A3ExtMapBundle value);
        <T extends Delegate> Saver putDelegate(final String key, final T value);
        Saver putByteArray(final String key, final byte[] value);
        Saver putShortArray(final String key, final short[] value);
        Saver putIntArray(final String key, final int[] value);
        Saver putLongArray(final String key, final long[] value);
        Saver putFloatArray(final String key, final float[] value);
        Saver putDoubleArray(final String key, final double[] value);
        Saver putBooleanArray(final String key, final boolean[] value);
        Saver putCharArray(final String key, final char[] value);
        Saver putStringArray(final String key, final String[] value);
        Saver putBigIntegerArray(final String key, final BigInteger[] value);
        Saver putBigDecimalArray(final String key, final BigDecimal[] value);
    }

    interface Restorer {
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
        A3ExtMapBundle getExtMapBundle(final String key, final A3ExtMapBundle defValue);
        <T extends Delegate> T getDelegate(final String key, final T defValue);
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

    A3ExtMapBundle putByte(final String key, final byte value);
    A3ExtMapBundle putShort(final String key, final short value);
    A3ExtMapBundle putInt(final String key, final int value);
    A3ExtMapBundle putLong(final String key, final long value);
    A3ExtMapBundle putFloat(final String key, final float value);
    A3ExtMapBundle putDouble(final String key, final double value);
    A3ExtMapBundle putBoolean(final String key, final boolean value);
    A3ExtMapBundle putChar(final String key, final char value);
    A3ExtMapBundle putString(final String key, final String value);
    A3ExtMapBundle putBigInteger(final String key, final BigInteger value);
    A3ExtMapBundle putBigDecimal(final String key, final BigDecimal value);
    A3ExtMapBundle putExtMapBundle(final String key, final A3ExtMapBundle value);
    <T extends Delegate> A3ExtMapBundle putDelegate(final String key, final T value);
    A3ExtMapBundle putByteArray(final String key, final byte[] value);
    A3ExtMapBundle putShortArray(final String key, final short[] value);
    A3ExtMapBundle putIntArray(final String key, final int[] value);
    A3ExtMapBundle putLongArray(final String key, final long[] value);
    A3ExtMapBundle putFloatArray(final String key, final float[] value);
    A3ExtMapBundle putDoubleArray(final String key, final double[] value);
    A3ExtMapBundle putBooleanArray(final String key, final boolean[] value);
    A3ExtMapBundle putCharArray(final String key, final char[] value);
    A3ExtMapBundle putStringArray(final String key, final String[] value);
    A3ExtMapBundle putBigIntegerArray(final String key, final BigInteger[] value);
    A3ExtMapBundle putBigDecimalArray(final String key, final BigDecimal[] value);

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
    A3ExtMapBundle getExtMapBundle(final String key, final A3ExtMapBundle defValue);
    <T extends Delegate> T getDelegate(final String key, final T defValue);
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

package com.ansdoship.a3wt.android;

import android.content.SharedPreferences;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.util.A3Arrays;
import com.ansdoship.a3wt.util.A3TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static com.ansdoship.a3wt.util.A3Preconditions.*;

public class AndroidA3Preferences implements A3Preferences {

    protected final SharedPreferences sharedPreferences;
    protected final SharedPreferences.Editor editor;
    protected final String name;

    public AndroidA3Preferences(final SharedPreferences sharedPreferences, final String name) {
        checkArgNotNull(sharedPreferences);
        checkArgNotEmpty(name);
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        this.name = name;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {
        return editor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public A3Preferences putByte(final String key, final byte value) {
        checkArgNotNull(key, "key");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putShort(final String key, final short value) {
        checkArgNotNull(key, "key");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putInt(final String key, final int value) {
        checkArgNotNull(key, "key");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putLong(final String key, final long value) {
        checkArgNotNull(key, "key");
        editor.putLong(key, value);
        return this;
    }

    @Override
    public A3Preferences putFloat(final String key, final float value) {
        checkArgNotNull(key, "key");
        editor.putFloat(key, value);
        return this;
    }

    @Override
    public A3Preferences putDouble(final String key, final double value) {
        checkArgNotNull(key, "key");
        editor.putString(key, Double.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBoolean(final String key, final boolean value) {
        checkArgNotNull(key, "key");
        editor.putBoolean(key, value);
        return this;
    }

    @Override
    public A3Preferences putChar(final String key, final char value) {
        checkArgNotNull(key, "key");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putString(final String key, final String value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, value);
        return this;
    }

    @Override
    public A3Preferences putBigInteger(final String key, final BigInteger value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, value.toString());
        return this;
    }

    @Override
    public A3Preferences putBigDecimal(final String key, final BigDecimal value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, value.toPlainString());
        return this;
    }

    @Override
    public A3Preferences putByteArray(final String key, final byte[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putShortArray(final String key, final short[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putIntArray(final String key, final int[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putLongArray(final String key, final long[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putFloatArray(final String key, final float[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putDoubleArray(final String key, final double[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBooleanArray(final String key, final boolean[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putCharArray(final String key, final char[] value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putStringArray(final String key, final String[] value) {
        checkArgNotNull(key, "key");
        checkArgElementsNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBigIntegerArray(final String key, final BigInteger[] value) {
        checkArgNotNull(key, "key");
        checkArgElementsNotNull(value, "value");
        editor.putString(key, Arrays.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBigDecimalArray(final String key, final BigDecimal[] value) {
        checkArgNotNull(key, "key");
        checkArgElementsNotNull(value, "value");
        editor.putString(key, A3Arrays.toPlainString(value));
        return this;
    }

    @Override
    public byte getByte(final String key, final byte defValue) {
        checkArgNotNull(key, "key");
        return (byte) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public short getShort(final String key, final short defValue) {
        checkArgNotNull(key, "key");
        return (short) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public int getInt(final String key, final int defValue) {
        checkArgNotNull(key, "key");
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(final String key, final long defValue) {
        checkArgNotNull(key, "key");
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        checkArgNotNull(key, "key");
        return sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public double getDouble(final String key, final double defValue) {
        checkArgNotNull(key, "key");
        return Double.parseDouble(sharedPreferences.getString(key, Double.toString(defValue)));
    }

    @Override
    public boolean getBoolean(final String key, final boolean defValue) {
        checkArgNotNull(key, "key");
        return sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public char getChar(final String key, final char defValue) {
        checkArgNotNull(key, "key");
        return (char) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public String getString(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(final String key, final BigInteger defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public byte[] getByteArray(final String key, final byte[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseByteArray(value);
    }

    @Override
    public short[] getShortArray(final String key, final short[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseShortArray(value);
    }

    @Override
    public int[] getIntArray(final String key, final int[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseIntArray(value);
    }

    @Override
    public long[] getLongArray(final String key, final long[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseLongArray(value);
    }

    @Override
    public float[] getFloatArray(final String key, final float[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseFloatArray(value);
    }

    @Override
    public double[] getDoubleArray(final String key, final double[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseDoubleArray(value);
    }

    @Override
    public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseBooleanArray(value);
    }

    @Override
    public char[] getCharArray(final String key, final char[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseCharArray(value);
    }

    @Override
    public String[] getStringArray(final String key, final String[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseStringArray(value);
    }

    @Override
    public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseBigIntegerArray(value);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
        checkArgNotNull(key, "key");
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : A3TextUtils.parseBigDecimalArray(value);
    }

    @Override
    public boolean contains(String key) {
        checkArgNotNull(key, "key");
        return sharedPreferences.contains(key);
    }

    @Override
    public A3Preferences remove(String key) {
        checkArgNotNull(key, "key");
        editor.remove(key);
        return this;
    }

    @Override
    public A3Preferences clear() {
        editor.clear();
        return this;
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }

    @Override
    public void apply() {
        editor.apply();
    }

}

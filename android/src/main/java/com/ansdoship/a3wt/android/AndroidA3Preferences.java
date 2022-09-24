package com.ansdoship.a3wt.android;

import android.content.SharedPreferences;
import com.ansdoship.a3wt.app.A3Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

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
        checkArgNotNull(value, "value");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putShort(final String key, final short value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putInt(final String key, final int value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putLong(final String key, final long value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putLong(key, value);
        return this;
    }

    @Override
    public A3Preferences putFloat(final String key, final float value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putFloat(key, value);
        return this;
    }

    @Override
    public A3Preferences putDouble(final String key, final double value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putString(key, Double.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBoolean(final String key, final boolean value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        editor.putBoolean(key, value);
        return this;
    }

    @Override
    public A3Preferences putChar(final String key, final char value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
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
        final String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : new BigDecimal(value);
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

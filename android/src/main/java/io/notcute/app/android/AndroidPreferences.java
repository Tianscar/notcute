package io.notcute.app.android;

import android.content.SharedPreferences;
import io.notcute.app.Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class AndroidPreferences implements Preferences {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final String name;

    public AndroidPreferences(SharedPreferences sharedPreferences, String name) {
        Objects.requireNonNull(sharedPreferences);
        Objects.requireNonNull(name);
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
    public Preferences putByte(String key, byte value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public Preferences putShort(String key, short value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public Preferences putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public Preferences putLong(String key, long value) {
        editor.putLong(key, value);
        return this;
    }

    @Override
    public Preferences putFloat(String key, float value) {
        editor.putFloat(key, value);
        return this;
    }

    @Override
    public Preferences putDouble(String key, double value) {
        editor.putString(key, Double.toString(value));
        return this;
    }

    @Override
    public Preferences putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return this;
    }

    @Override
    public Preferences putChar(String key, char value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public Preferences putString(String key, String value) {
        editor.putString(key, value);
        return this;
    }

    @Override
    public Preferences putBigInteger(String key, BigInteger value) {
        editor.putString(key, value.toString());
        return this;
    }

    @Override
    public Preferences putBigDecimal(String key, BigDecimal value) {
        editor.putString(key, value.toPlainString());
        return this;
    }

    @Override
    public byte getByte(String key, byte defValue) {
        return (byte) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public short getShort(String key, short defValue) {
        return (short) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public double getDouble(String key, double defValue) {
        String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : Double.parseDouble(value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public char getChar(String key, char defValue) {
        return (char) sharedPreferences.getInt(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defValue) {
        String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        String value = sharedPreferences.getString(key, null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public Preferences remove(String key) {
        editor.remove(key);
        return this;
    }

    @Override
    public Preferences clear() {
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

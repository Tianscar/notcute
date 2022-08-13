package com.ansdoship.a3wt.android;

import android.content.SharedPreferences;
import com.ansdoship.a3wt.app.A3Preferences;

public class AndroidA3Preferences implements A3Preferences {

    protected final SharedPreferences sharedPreferences;
    protected final SharedPreferences.Editor editor;
    protected final String name;

    public AndroidA3Preferences(SharedPreferences sharedPreferences, String name) {
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
    public A3Preferences putByte(String key, byte value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putShort(String key, short value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putLong(String key, long value) {
        editor.putLong(key, value);
        return this;
    }

    @Override
    public A3Preferences putFloat(String key, float value) {
        editor.putFloat(key, value);
        return this;
    }

    @Override
    public A3Preferences putDouble(String key, double value) {
        editor.putString(key, Double.toString(value));
        return this;
    }

    @Override
    public A3Preferences putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return this;
    }

    @Override
    public A3Preferences putChar(String key, char value) {
        editor.putInt(key, value);
        return this;
    }

    @Override
    public A3Preferences putString(String key, String value) {
        editor.putString(key, value);
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
        return Double.parseDouble(sharedPreferences.getString(key, Double.toString(defValue)));
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
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public A3Preferences remove(String key) {
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

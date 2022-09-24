package com.ansdoship.a3wt.app;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface A3Preferences {

    String getName();

    A3Preferences putByte(String key, byte value);
    A3Preferences putShort(String key, short value);
    A3Preferences putInt(String key, int value);
    A3Preferences putLong(String key, long value);
    A3Preferences putFloat(String key, float value);
    A3Preferences putDouble(String key, double value);
    A3Preferences putBoolean(String key, boolean value);
    A3Preferences putChar(String key, char value);
    A3Preferences putString(String key, String value);
    A3Preferences putBigInteger(String key, BigInteger value);
    A3Preferences putBigDecimal(String key, BigDecimal value);

    byte getByte(String key, byte defValue);
    short getShort(String key, short defValue);
    int getInt(String key, int defValue);
    long getLong(String key, long defValue);
    float getFloat(String key, float defValue);
    double getDouble(String key, double defValue);
    char getChar(String key, char defValue);
    String getString(String key, String defValue);
    BigInteger getBigInteger(String key, BigInteger defValue);
    BigDecimal getBigDecimal(String key, BigDecimal defValue);

    boolean contains(String key);
    A3Preferences remove(String key);
    A3Preferences clear();

    boolean commit();
    void apply();

}

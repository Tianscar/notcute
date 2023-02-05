package io.notcute.app;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Preferences {

    String getName();

    Preferences putByte(String key, byte value);
    Preferences putShort(String key, short value);
    Preferences putInt(String key, int value);
    Preferences putLong(String key, long value);
    Preferences putFloat(String key, float value);
    Preferences putDouble(String key, double value);
    Preferences putBoolean(String key, boolean value);
    Preferences putChar(String key, char value);
    Preferences putString(String key, String value);
    Preferences putBigInteger(String key, BigInteger value);
    Preferences putBigDecimal(String key, BigDecimal value);

    byte getByte(String key, byte defValue);
    short getShort(String key, short defValue);
    int getInt(String key, int defValue);
    long getLong(String key, long defValue);
    float getFloat(String key, float defValue);
    double getDouble(String key, double defValue);
    boolean getBoolean(String key, boolean defValue);
    char getChar(String key, char defValue);
    String getString(String key, String defValue);
    BigInteger getBigInteger(String key, BigInteger defValue);
    BigDecimal getBigDecimal(String key, BigDecimal defValue);

    boolean contains(String key);
    Preferences remove(String key);
    Preferences clear();

    boolean commit();
    void apply();

}

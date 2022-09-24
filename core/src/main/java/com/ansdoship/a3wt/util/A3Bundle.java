package com.ansdoship.a3wt.util;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

public interface A3Bundle {

    String getFormat();

    A3Bundle putByte(String key, byte value);
    A3Bundle putShort(String key, short value);
    A3Bundle putInt(String key, int value);
    A3Bundle putLong(String key, long value);
    A3Bundle putFloat(String key, float value);
    A3Bundle putDouble(String key, double value);
    A3Bundle putBoolean(String key, boolean value);
    A3Bundle putChar(String key, char value);
    A3Bundle putString(String key, String value);
    A3Bundle putBigInteger(String key, BigInteger value);
    A3Bundle putBigDecimal(String key, BigDecimal value);
    A3Bundle putBundleable(String key, A3Bundleable value);

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
    A3Bundleable getBundleable(String key, A3Bundleable defValue);

    boolean contains(String key);
    A3Bundle remove(String key);
    A3Bundle clear();

    A3Bundle read(File input);
    A3Bundle read(InputStream input);
    A3Bundle read(URL input);
    A3Bundle read(A3Assets assets, String input);

    boolean write(File output);
    boolean write(OutputStream output);
    
}

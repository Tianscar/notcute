package io.notcute.internal.awt.win32;

import jnr.ffi.util.EnumMapper;

public enum MONITOR_DPI_TYPE implements EnumMapper.IntegerEnum {

    MDT_EFFECTIVE_DPI(0),
    MDT_ANGULAR_DPI(1),
    MDT_RAW_DPI(2),
    MDT_DEFAULT(0);

    private final int value;

    MONITOR_DPI_TYPE(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

}

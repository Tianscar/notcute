package io.notcute.internal.awt.x11;

import jnr.ffi.util.EnumMapper;

public enum GtkResponseType implements EnumMapper.IntegerEnum {

    GTK_RESPONSE_NONE (-1),
    GTK_RESPONSE_REJECT (-2),
    GTK_RESPONSE_ACCEPT (-3),
    GTK_RESPONSE_DELETE_EVENT (-4),
    GTK_RESPONSE_OK (-5),
    GTK_RESPONSE_CANCEL (-6),
    GTK_RESPONSE_CLOSE (-7),
    GTK_RESPONSE_YES (-8),
    GTK_RESPONSE_NO (-9),
    GTK_RESPONSE_APPLY (-10),
    GTK_RESPONSE_HELP (-11);
    
    private final int value;

    GtkResponseType(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

}

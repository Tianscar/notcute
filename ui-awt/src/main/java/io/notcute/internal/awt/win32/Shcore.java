package io.notcute.internal.awt.win32;

import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.IntByReference;

public interface Shcore {

    Shcore INSTANCE = Win32Utils.initShcore();

    int GetDpiForMonitor(
            @In long hmonitor,
            @In MONITOR_DPI_TYPE dpiType,
            @Out IntByReference dpiX,
            @Out IntByReference dpiY
    );

}

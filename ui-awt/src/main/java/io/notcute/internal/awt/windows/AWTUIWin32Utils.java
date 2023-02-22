package io.notcute.internal.awt.windows;

import io.notcute.internal.desktop.win32.MONITOR_DPI_TYPE;
import io.notcute.internal.desktop.win32.Shcore;
import io.notcute.internal.desktop.win32.User32;
import io.notcute.internal.desktop.win32.Win32ErrorConstants;
import jnr.ffi.byref.IntByReference;
import sun.awt.AWTAccessor;

import java.awt.*;
import java.awt.peer.ComponentPeer;

public final class AWTUIWin32Utils {

    private static final long NULL = 0L;

    private AWTUIWin32Utils() {
        throw new UnsupportedOperationException();
    }

    public static long getHWnd(Component component) {
        if (component == null) return NULL;
        try {
            ComponentPeer peer = AWTAccessor.getComponentAccessor().getPeer(component);
            return (long) peer.getClass().getMethod("getHWnd").invoke(peer);
        }
        catch (Exception ignored) {
            return NULL;
        }
    }

    public static int getDpiForComponent(Component component) {
        if (component == null) return Toolkit.getDefaultToolkit().getScreenResolution();
        Shcore SHCORE = Shcore.INSTANCE;
        if (SHCORE != null) {
            long hWnd = getHWnd(component);
            if (hWnd != NULL) {
                User32 USER32 = User32.INSTANCE;
                long monitor = USER32.MonitorFromWindow(hWnd, User32.MONITOR_DEFAULTTOPRIMARY);
                IntByReference dpiX = new IntByReference();
                IntByReference dpiY = new IntByReference();
                int hresult = SHCORE.GetDpiForMonitor(monitor, MONITOR_DPI_TYPE.MDT_EFFECTIVE_DPI, dpiX, dpiY);
                if (hresult == Win32ErrorConstants.S_OK) return dpiX.intValue();
            }
        }
        return component.getToolkit().getScreenResolution();
    }

}

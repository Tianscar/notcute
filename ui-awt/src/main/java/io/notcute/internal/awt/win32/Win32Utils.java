package io.notcute.internal.awt.win32;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.byref.IntByReference;
import sun.awt.AWTAccessor;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.peer.ComponentPeer;

public final class Win32Utils {

    private static final long NULL = 0L;

    private Win32Utils() {
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

    static Shcore initShcore() {
        try {
            return Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS ?
                    LibraryLoader.create(Shcore.class).load("Shcore") : null;
        }
        catch (Exception e) {
            return null;
        }
    }

}

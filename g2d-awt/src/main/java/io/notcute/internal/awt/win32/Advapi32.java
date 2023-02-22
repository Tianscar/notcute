package io.notcute.internal.awt.win32;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.Pointer;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.IntByReference;

public interface Advapi32 {

    Advapi32 INSTANCE = Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS ?
            LibraryLoader.create(Advapi32.class).load("Advapi32") : null;

    int HKEY_CURRENT_USER = 0x80000001;
    int KEY_READ = 0x20019;

    int RegOpenKeyExW(
            @In int hKey,
            @In String lpSubKey,
            @In int ulOptions,
            @In int samDesired,
            @Out IntByReference phkResult
    );

    int RegQueryValueExW(
            @In int hKey,
            @In String lpValueName,
            @In long lpReserved,
            @Out Pointer lpType,
            @Out Pointer lpData,
            Pointer lpcbData
    );

    int RegCloseKey(
            @In long hKey
    );


}

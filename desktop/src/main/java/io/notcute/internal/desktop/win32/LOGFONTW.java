package io.notcute.internal.desktop.win32;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class LOGFONTW extends Struct {
    LONG lfHeight = new LONG();
    LONG lfWidth = new LONG();
    LONG lfEscapement = new LONG();
    LONG lfOrientation = new LONG();
    LONG lfWeight = new LONG();
    BYTE lfItalic = new BYTE();
    BYTE lfUnderline = new BYTE();
    BYTE lfStrikeOut = new BYTE();
    BYTE lfCharSet = new BYTE();
    BYTE lfOutPrecision = new BYTE();
    BYTE lfClipPrecision = new BYTE();
    BYTE lfQuality = new BYTE();
    BYTE lfPitchAndFamily = new BYTE();
    Unsigned16[] lfFaceName = array(new Unsigned16[32]);

    public LOGFONTW(Runtime runtime) {
        super(runtime);
    }
}

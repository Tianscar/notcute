package io.notcute.internal.awt.win32;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class NONCLIENTMETRICSW extends Struct {
    Unsigned32 cbSize = new Unsigned32();
    Signed32 iBorderWidth = new Signed32();
    Signed32 iScrollWidth = new Signed32();
    Signed32 iScrollHeight = new Signed32();
    Signed32 iCaptionWidth = new Signed32();
    Signed32 iCaptionHeight = new Signed32();
    LOGFONTW lfCaptionFont = inner(LOGFONTW.class);
    Signed32 iSmCaptionWidth = new Signed32();
    Signed32 iSmCaptionHeight = new Signed32();
    LOGFONTW lfSmCaptionFont = inner(LOGFONTW.class);
    Signed32 iMenuWidth = new Signed32();
    Signed32 iMenuHeight = new Signed32();
    LOGFONTW lfMenuFont = inner(LOGFONTW.class);
    LOGFONTW lfStatusFont = inner(LOGFONTW.class);
    LOGFONTW lfMessageFont = inner(LOGFONTW.class);
    Signed32 iPaddedBorderWidth = new Signed32();

    public NONCLIENTMETRICSW(Runtime runtime) {
        super(runtime);
    }
}

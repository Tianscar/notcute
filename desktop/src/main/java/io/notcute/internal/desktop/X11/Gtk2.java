package io.notcute.internal.desktop.X11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.annotations.In;

public interface Gtk2 extends Gtk {


    //Gtk2 INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
    //        ? null : LibraryLoader.create(Gtk2.class).load("gtk-x11-2.0");

    long
    gtk_file_filter_new ();

    void
    gtk_file_filter_add_mime_type (
            @In long filter,
            @In String mime_type
    );

    void
    gtk_file_filter_set_name (
            @In long filter,
            @In String name
    );

    void
    gtk_widget_set_has_window (
            @In long widget,
            @In boolean has_window
    );

    void
    gtk_widget_set_window (
            @In long widget,
            @In long window
    );

}

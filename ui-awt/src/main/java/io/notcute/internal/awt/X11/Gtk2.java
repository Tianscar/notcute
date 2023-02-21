package io.notcute.internal.awt.X11;

import jnr.ffi.annotations.In;

public interface Gtk2 extends Gtk {

    /*
    Gtk2 INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
            ? null : (GtkUtils.getGtkMajorVersion() == 2 ? LibraryLoader.create(Gtk2.class).load("gtk-x11-2.0") : null);
     */

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

    long
    gdk_display_get_default ();

    long
    gdk_x11_window_foreign_new_for_display (
            @In long display,
            @In long window
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

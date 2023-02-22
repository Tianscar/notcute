package io.notcute.internal.desktop.X11;

import jnr.ffi.annotations.In;

public interface Gtk extends Gdk, Gio {

    void
    gtk_widget_show_all (
            @In long widget
    );

    long
    gtk_window_new (
            @In GtkWindowType type
    );

    void
    gtk_widget_realize (
            @In long widget
    );

    boolean
    gtk_main_iteration ();

    boolean
    gtk_events_pending ();

}

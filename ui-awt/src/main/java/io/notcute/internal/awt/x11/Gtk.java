package io.notcute.internal.awt.x11;

import jnr.ffi.annotations.In;

public interface Gtk {

    void
    g_free (
            @In long mem
    );

    void
    g_slist_free (
            GSList list
    );

    void
    g_object_unref (
            @In long object
    );

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

package io.notcute.internal.desktop.X11;

import jnr.ffi.annotations.In;

public interface Gtk3 extends Gtk2 {

    Gtk3 INSTANCE = GtkUtils.initGtk3();

    long
    gtk_file_chooser_native_new (
            @In String title,
            @In long parent,
            @In GtkFileChooserAction action,
            @In String accept_label,
            @In String cancel_label
    );

    void
    gtk_file_chooser_set_select_multiple (
            @In long chooser,
            @In boolean select_multiple
    );

    boolean
    gtk_file_chooser_set_current_folder (
            @In long chooser,
            @In String filename
    );

    void
    gtk_file_chooser_add_filter (
            @In long chooser,
            @In long filter
    );

    GtkResponseType
    gtk_native_dialog_run (
            @In long dialog
    );

    GSList
    gtk_file_chooser_get_filenames (
            @In long chooser
    );

}

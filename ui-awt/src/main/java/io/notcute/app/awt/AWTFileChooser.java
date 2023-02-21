package io.notcute.app.awt;

import io.notcute.app.FileChooser;
import io.notcute.internal.awt.AWTUIUtils;
import io.notcute.internal.awt.X11.*;
import io.notcute.ui.Container;
import io.notcute.ui.awt.AWTContainer;
import io.notcute.util.MIMETypes;
import io.notcute.util.signalslot.*;
import jnr.ffi.Runtime;

import java.awt.FileDialog;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AWTFileChooser implements FileChooser {

    private static final SimpleDispatcher DISPATCHER = new SimpleDispatcher("AWT-FileChooser");
    static {
        DISPATCHER.start();
    }
    private final VoidSignal1<FileDialog> onShow = new VoidSignal1<>();
    {
        onShow.connect(new VoidSlot1<FileDialog>() {
            @Override
            public void accept(FileDialog fileDialog) {
                fileDialog.setVisible(true);
                onFileChosen.emit(fileDialog.getFiles());
                fileDialog.dispose();
            }
        }, DISPATCHER);
    }

    private final VoidSignal2<AWTContainer, Long> onShowGtk3 = new VoidSignal2<>();
    {
        onShowGtk3.connect(new VoidSlot2<AWTContainer, Long>() {
            @Override
            public void accept(AWTContainer container, Long window) {
                CharSequence title = info.getTitle();
                String[] filterMIMETypes = info.getFilterMIMETypes();
                File pathname = info.getPathname();
                Gtk3 GTK3 = Gtk3.INSTANCE;
                long gdkDisplay = GTK3.gdk_display_get_default();
                long gdkWindow = GTK3.gdk_x11_window_foreign_new_for_display(gdkDisplay, window);
                long gtkWindow = GTK3.gtk_window_new(GtkWindowType.GTK_WINDOW_TOPLEVEL);
                GTK3.gtk_widget_set_has_window(gtkWindow, true);
                GTK3.gtk_widget_realize(gtkWindow);
                GTK3.gtk_widget_set_window(gtkWindow, gdkWindow);
                GTK3.gtk_widget_show_all(gtkWindow);
                long fileChooser = GTK3.gtk_file_chooser_native_new(
                        title == null ? "" : title.toString(),
                        gtkWindow,
                        info.getMode() == Mode.SAVE ? GtkFileChooserAction.GTK_FILE_CHOOSER_ACTION_SAVE :
                                GtkFileChooserAction.GTK_FILE_CHOOSER_ACTION_OPEN,
                        null, null);
                GTK3.gtk_file_chooser_set_select_multiple(fileChooser, info.isMultiple());
                if (filterMIMETypes != null && filterMIMETypes.length > 0) {
                    long fileFilter = GTK3.gtk_file_filter_new();
                    StringBuilder filterName = new StringBuilder();
                    for (String mimeType : filterMIMETypes) {
                        if (mimeType == null) continue;
                        GTK3.gtk_file_filter_add_mime_type(fileFilter, mimeType);
                        filterName.append(mimeType).append(", ");
                    }
                    if (filterName.length() >= 2)
                        filterName.deleteCharAt(filterName.length() - 1).deleteCharAt(filterName.length() - 1);
                    GTK3.gtk_file_filter_set_name(fileFilter, filterName.toString());
                    GTK3.gtk_file_chooser_add_filter(fileChooser, fileFilter);
                }
                if (pathname != null) GTK3.gtk_file_chooser_set_current_folder(fileChooser,
                        pathname.isFile() ? pathname.getParent() : pathname.getAbsolutePath());
                Connection conn = container.getContainerHolder()
                        .onCloseRequest().connect(container1 -> false); // make the parent container ignore close request
                GtkResponseType result = GTK3.gtk_native_dialog_run(fileChooser);
                while (GTK3.gtk_events_pending()) {
                    GTK3.gtk_main_iteration();
                }
                Set<String> pathsSet = new HashSet<>();
                if (result == GtkResponseType.GTK_RESPONSE_ACCEPT) {
                    GSList list = GTK3.gtk_file_chooser_get_filenames(fileChooser);
                    GSList file = list;
                    while (file != null) {
                        pathsSet.add(file.data.get().getString(0));
                        GTK3.g_free(file.data.get().address());
                        if (file.next.get() != null) {
                            GSList gsList = new GSList(Runtime.getRuntime(GTK3));
                            gsList.useMemory(file.next.get());
                            file = gsList;
                        }
                        else file = null;
                    }
                    GTK3.g_slist_free(list);
                }
                GTK3.g_object_unref(fileChooser);
                GTK3.g_object_unref(gdkWindow);
                String[] paths = pathsSet.toArray(new String[0]);
                File[] files = new File[paths.length];
                for (int i = 0; i < paths.length; i ++) {
                    files[i] = new File(paths[i]);
                }
                onFileChosen.emit(files);
                container.getContainerHolder().onCloseRequest().disconnect(conn);
            }
        }, DISPATCHER);
    }

    private final Info info;
    public AWTFileChooser() {
        info = new Info();
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public void attachContainer(Container container) {
        Objects.requireNonNull(container);
        if (AWTPlatform.isX11 && GtkUtils.loadGtk() && GtkUtils.getGtkMajorVersion() == 3) {
            long window = X11Utils.getXWindow((AWTContainer) container);
            if (window != 0L) {
                onShowGtk3.emit((AWTContainer) container, window);
                return;
            }
        }
        CharSequence title = info.getTitle();
        String[] filterMIMETypes = info.getFilterMIMETypes();
        File pathname = info.getPathname();
        FileDialog fileDialog = new FileDialog((AWTContainer) container, title == null ? "" : title.toString(),
                AWTUIUtils.toAWTFileDialogMode(info.getMode()));
        fileDialog.setMultipleMode(info.isMultiple());
        fileDialog.setDirectory(pathname == null ? null : (pathname.isDirectory() ? pathname.getAbsolutePath() : pathname.getParent()));
        if (filterMIMETypes != null) {
            MIMETypes mimeTypes = container.getContextHolder().getMIMETypes();
            StringBuilder extensions = new StringBuilder();
            for (String mimeType : filterMIMETypes) {
                for (String extension : mimeTypes.getExtensions(mimeType)) {
                    extensions.append("*.").append(extension).append(", ");
                }
            }
            if (extensions.length() > 0) extensions
                    .deleteCharAt(extensions.length() - 1)
                    .deleteCharAt(extensions.length() - 1);
            if (AWTPlatform.isWindows) fileDialog.setFile(extensions.toString());
            else {
                PathMatcher matcher = FileSystems.getDefault()
                        .getPathMatcher("glob:" + "*.{" +
                                extensions.toString().replaceAll("\\*.", "")
                                        .replaceAll(", ", ",")
                                + "}");
                fileDialog.setFilenameFilter((dir, name) -> matcher.matches(new File(dir, name).toPath().getFileName()));
            }
        }
        fileDialog.setLocation(-1, -1); // Make the FileDialog center of the screen
        onShow.emit(fileDialog);
    }

    private final VoidSignal1<File[]> onFileChosen = new VoidSignal1<>();
    @Override
    public VoidSignal1<File[]> onFileChosen() {
        return onFileChosen;
    }

}

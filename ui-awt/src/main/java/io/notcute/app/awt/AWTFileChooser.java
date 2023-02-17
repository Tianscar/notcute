package io.notcute.app.awt;

import io.notcute.app.FileChooser;
import io.notcute.internal.awt.AWTUIUtils;
import io.notcute.ui.Container;
import io.notcute.ui.awt.AWTContainer;
import io.notcute.util.Charsets;
import io.notcute.util.IOUtils;
import io.notcute.util.MIMETypes;
import io.notcute.util.signalslot.*;

import java.awt.FileDialog;
import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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

    private final VoidSignal2<AWTContainer, Process> onShowKDE = new VoidSignal2<>();
    {
        onShowKDE.connect(new VoidSlot2<AWTContainer, Process>() {
            @Override
            public void accept(AWTContainer container, Process process) {
                try {
                    Thread shutdownHook = new Thread(process::destroy); // To prevent the kdialog process become orphan process
                    Runtime.getRuntime().addShutdownHook(shutdownHook);
                    Connection conn = container.getContainerHolder()
                            .onCloseRequest().connect(container1 -> false); // make the parent container ignore close request
                    process.waitFor();
                    String[] paths = new String(IOUtils.readAllBytes(process.getInputStream()), Charsets.UTF_8).split("\n");
                    Runtime.getRuntime().removeShutdownHook(shutdownHook); // The kdialog process exited, so remove the shutdown hook
                    File[] files = new File[paths.length];
                    for (int i = 0; i < paths.length; i ++) {
                        files[i] = new File(paths[i]);
                    }
                    onFileChosen.emit(files);
                    container.getContainerHolder()
                            .onCloseRequest().disconnect(conn);
                } catch (InterruptedException | IOException ignored) {
                }
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
        CharSequence title = info.getTitle();
        String[] filterMIMETypes = info.getFilterMIMETypes();
        File pathname = info.getPathname();
        if (!AWTPlatform.isX11 && "KDE".equals(System.getenv("XDG_CURRENT_DESKTOP"))) {
            // AWT's FileDialog is not native on KDE, we fix it by call kdialog via Java Process API
            long window = getX11Window((AWTContainer) container);
            if (window != 0L) {
                try {
                    String titleOption = title == null ? "" : "--title";
                    String modeOption;
                    int mode = info.getMode();
                    switch (mode) {
                        case Mode.LOAD:
                            modeOption = "--getopenfilename";
                            break;
                        case Mode.SAVE:
                            modeOption = "--getsavefilename";
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid mode: " + mode);
                    }
                    StringBuilder mimeTypes = new StringBuilder();
                    if (filterMIMETypes != null) {
                        for (String mimeType : filterMIMETypes) {
                            mimeTypes.append(mimeType).append(" ");
                        }
                        if (mimeTypes.length() > 0) mimeTypes.deleteCharAt(mimeTypes.length() - 1);
                    }
                    Process process = new ProcessBuilder(
                            "kdialog",
                            "--attach",
                            Long.toString(window),
                            titleOption,
                            title == null ? "" : title.toString(),
                            modeOption,
                            pathname == null ? System.getProperty("user.dir") : pathname.getAbsolutePath(),
                            mimeTypes.toString(),
                            info.isMultiple() ? "--multiple" : "",
                            "--separate-output"
                    ).start();
                    onShowKDE.emit((AWTContainer) container, process);
                    return;
                } catch (IOException ignored) {
                    // kdialog not exist, or other io exception, fallback to AWT's FileDialog
                }
            }
        }
        FileDialog fileDialog = new FileDialog((AWTContainer) container, title == null ? "" : title.toString(), AWTUIUtils.toAWTFileDialogMode(info.getMode()));
        fileDialog.setMultipleMode(info.isMultiple());
        fileDialog.setDirectory(pathname == null ? null : (pathname.isDirectory() ? pathname.getAbsolutePath() : pathname.getParent()));
        if (filterMIMETypes != null) {
            MIMETypes mimeTypes = container.getContextHolder().getMIMETypes();
            if (AWTPlatform.isWindows) {
                StringBuilder extensions = new StringBuilder();
                for (String mimeType : filterMIMETypes) {
                    for (String extension : mimeTypes.getExtensions(mimeType)) {
                        extensions.append("*.").append(extension).append(", ");
                    }
                }
                if (extensions.length() > 0) extensions
                        .deleteCharAt(extensions.length() - 1)
                        .deleteCharAt(extensions.length() - 1);
                fileDialog.setFile(extensions.toString());
            }
            else {
                Set<String> filterMIMETypeSet = new HashSet<>(Arrays.asList(filterMIMETypes));
                fileDialog.setFilenameFilter(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        for (String mimeType : mimeTypes.getMIMETypes(name.substring(name.lastIndexOf('.')))) {
                            if (filterMIMETypeSet.contains(mimeType)) return true;
                        }
                        return false;
                    }
                });
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

    private static long getX11Window(Component component) {
        if (component == null) return 0L;
        try {
            Field peer = Component.class.getDeclaredField("peer");
            peer.setAccessible(true);
            return (long) Class.forName("sun.awt.X11.XBaseWindow").getDeclaredMethod("getWindow").invoke(peer.get(component));
        }
        catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InaccessibleObjectException ignored) {
            return 0L;
        }
    }

}

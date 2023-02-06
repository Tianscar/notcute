package io.notcute.app.awt;

import io.notcute.app.FileChooser;
import io.notcute.ui.Container;
import io.notcute.ui.awt.AWTContainer;
import io.notcute.util.signalslot.SimpleDispatcher;
import io.notcute.util.signalslot.VoidSignal1;
import io.notcute.util.signalslot.VoidSlot1;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

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
        CharSequence title = info.getTitle();
        FileFilter fileFilter = info.getFileFilter();
        File directory = info.getDirectory();
        File file = info.getFile();
        FileDialog fileDialog = new FileDialog((AWTContainer) container, title == null ? "" : title.toString(), Util.toAWTFileDialogMode(info.getMode()));
        fileDialog.setMultipleMode(info.isMultiple());
        fileDialog.setDirectory(directory == null ? null : directory.getAbsolutePath());
        fileDialog.setFile(file == null ? null : file.getAbsolutePath());
        if (fileFilter != null) {
            fileDialog.setFilenameFilter(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return fileFilter.accept(new File(dir, name));
                }
            });
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

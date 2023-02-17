package io.notcute.app.swt;

import io.notcute.app.FileChooser;
import io.notcute.ui.Container;
import io.notcute.util.signalslot.VoidSignal1;
import org.eclipse.swt.widgets.FileDialog;

import java.io.File;

public class SWTFileChooser implements FileChooser {

    @Override
    public Info getInfo() {
        return null;
    }

    @Override
    public void attachContainer(Container container) {
        //FileDialog fileDialog = new FileDialog(((SWTContainer) container).getShell());
        //fileDialog.setFilterExtensions();
    }

    @Override
    public VoidSignal1<File[]> onFileChosen() {
        return null;
    }

}

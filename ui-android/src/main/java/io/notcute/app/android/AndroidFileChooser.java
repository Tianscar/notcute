package io.notcute.app.android;

import io.notcute.app.FileChooser;
import io.notcute.ui.Container;
import io.notcute.util.signalslot.VoidSignal1;

import java.io.File;

public class AndroidFileChooser implements FileChooser {

    private final Info info;
    public AndroidFileChooser() {
        info = new Info();
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public void attachContainer(Container container) {

    }

    private final VoidSignal1<File[]> onFileChosen = new VoidSignal1<>();
    @Override
    public VoidSignal1<File[]> onFileChosen() {
        return onFileChosen;
    }

}

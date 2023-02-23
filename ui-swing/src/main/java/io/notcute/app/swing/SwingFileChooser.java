package io.notcute.app.swing;

import io.notcute.app.FileChooser;
import io.notcute.ui.Container;
import io.notcute.util.signalslot.VoidSignal1;

import javax.swing.JFileChooser;
import java.io.File;

public class SwingFileChooser implements FileChooser {

    private final Info info;

    public SwingFileChooser() {
        info = new Info();
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public void attachContainer(Container container) {
        JFileChooser fileChooser = new JFileChooser();

    }

    private final VoidSignal1<File[]> onFileChosen = new VoidSignal1<>();
    @Override
    public VoidSignal1<File[]> onFileSelected() {
        return onFileChosen;
    }

}

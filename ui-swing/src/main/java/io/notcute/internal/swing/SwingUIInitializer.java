package io.notcute.internal.swing;

import io.notcute.app.FileChooser;
import io.notcute.app.Platform;
import io.notcute.app.swing.SwingFileChooser;
import io.notcute.app.swing.SwingPlatform;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;

public class SwingUIInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "platform"), SwingUIInitializer::getPlatform);
        Producer.GLOBAL.put(new Identifier("notcute", "fileChooser"), SwingUIInitializer::getFileChooser);
    }

    private static volatile SwingPlatform platform = null;
    public static Platform getPlatform() {
        if (platform == null) platform = new SwingPlatform();
        return platform;
    }

    private static volatile SwingFileChooser fileChooser = null;
    public static FileChooser getFileChooser() {
        if (fileChooser == null) fileChooser = new SwingFileChooser();
        return fileChooser;
    }

}

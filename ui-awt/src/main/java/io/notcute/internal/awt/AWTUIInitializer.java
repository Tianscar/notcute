package io.notcute.internal.awt;

import io.notcute.app.FileChooser;
import io.notcute.app.Platform;
import io.notcute.app.awt.AWTFileChooser;
import io.notcute.app.awt.AWTPlatform;
import io.notcute.audio.AudioPlayer;
import io.notcute.audio.javase.JavaSEAudioPlayer;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;
import io.notcute.ui.UIKit;
import io.notcute.ui.awt.AWTUIKit;

public class AWTUIInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "platform"), AWTUIInitializer::getPlatform);
        Producer.GLOBAL.put(new Identifier("notcute", "uiKit"), AWTUIInitializer::getUIKit);
        Producer.GLOBAL.put(new Identifier("notcute", "audioPlayer"), AWTUIInitializer::getAudioPlayer);
        Producer.GLOBAL.put(new Identifier("notcute", "fileChooser"), AWTUIInitializer::getFileChooser);
    }

    private static volatile AWTPlatform platform = null;
    public synchronized static Platform getPlatform() {
        if (platform == null) platform = new AWTPlatform();
        return platform;
    }

    private static volatile AWTUIKit uiKit = null;
    public synchronized static UIKit getUIKit() {
        if (uiKit == null) uiKit = new AWTUIKit();
        return uiKit;
    }

    private static volatile JavaSEAudioPlayer audioPlayer = null;
    public synchronized static AudioPlayer getAudioPlayer() {
        if (audioPlayer == null) audioPlayer = new JavaSEAudioPlayer();
        return audioPlayer;
    }

    private static volatile AWTFileChooser fileChooser = null;
    public synchronized static FileChooser getFileChooser() {
        if (fileChooser == null) fileChooser = new AWTFileChooser();
        return fileChooser;
    }

}

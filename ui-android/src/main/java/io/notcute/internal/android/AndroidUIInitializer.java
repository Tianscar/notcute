package io.notcute.internal.android;

import android.annotation.SuppressLint;
import io.notcute.audio.AudioPlayer;
import io.notcute.audio.android.AndroidAudioPlayer;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.android.AndroidGraphicsKit;
import io.notcute.ui.UIKit;
import io.notcute.ui.android.AndroidUIKit;

import static io.notcute.internal.android.AndroidShared.getContext;
import static io.notcute.internal.android.AndroidShared.hasContext;

public class AndroidUIInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "graphicsKit"), AndroidUIInitializer::getGraphicsKit);
        Producer.GLOBAL.put(new Identifier("notcute", "uiKit"), AndroidUIInitializer::getUIKit);
        Producer.GLOBAL.put(new Identifier("notcute", "audioPlayer"), AndroidUIInitializer::getAudioPlayer);
    }

    private static volatile AndroidGraphicsKit graphicsKit = null;
    public static GraphicsKit getGraphicsKit() {
        if (graphicsKit == null) graphicsKit = new AndroidGraphicsKit();
        return graphicsKit;
    }

    @SuppressLint("StaticFieldLeak")
    private static volatile AndroidUIKit uiKit = null;
    public static UIKit getUIKit() {
        if (uiKit == null && hasContext()) uiKit = new AndroidUIKit(getContext());
        return uiKit;
    }

    private static volatile AndroidAudioPlayer audioPlayer = null;
    public static AudioPlayer getAudioPlayer() {
        if (audioPlayer == null) audioPlayer = new AndroidAudioPlayer();
        return audioPlayer;
    }

    public static void releaseContext() {
        uiKit = null;
    }

}

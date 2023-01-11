package a3wt.audio;

import a3wt.app.A3Assets;

import java.io.File;

public interface A3AudioKit {

    A3Sound readSound(final File input);
    A3Sound readSound(final A3Assets assets, final String input);

    A3Music readMusic(final File input);
    A3Music readMusic(final A3Assets assets, final String input);

    String[] getAudioReaderFormatNames();

}

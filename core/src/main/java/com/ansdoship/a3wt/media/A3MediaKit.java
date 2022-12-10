package com.ansdoship.a3wt.media;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public interface A3MediaKit {

    A3Audio readAudio(final File input);
    A3Audio readAudio(final InputStream input);
    A3Audio readAudio(final URL input);
    A3Audio readAudio(final A3Assets assets, final String input);

    boolean writeAudio(final A3Audio audio, final String formatName, final A3Audio.Format format, final File output);
    default boolean writeAudio(final A3Audio audio, final String formatName, final File output) {
        return writeAudio(audio, formatName, audio.getFormat(), output);
    }
    boolean writeAudio(final A3Audio audio, final String formatName, final A3Audio.Format format, final OutputStream output);
    default boolean writeAudio(final A3Audio audio, final String formatName, final OutputStream output) {
        return writeAudio(audio, formatName, audio.getFormat(), output);
    }

    default A3Audio.Format createAudioFormat() {
        return new A3Audio.DefaultFormat();
    }
    default A3Audio.Format createAudioFormat(final String encoding,
                                             final float sampleRate,
                                             final int sampleSizeInBits,
                                             final int channels,
                                             final int frameSize,
                                             final float frameRate,
                                             final boolean bigEndian,
                                             final Map<String, Object> properties) {
        return new A3Audio.DefaultFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian, properties);
    }
    default A3Audio.Format createAudioFormat(final String encoding,
                                             final float sampleRate,
                                             final int sampleSizeInBits,
                                             final int channels,
                                             final int frameSize,
                                             final float frameRate,
                                             final boolean bigEndian) {
        return new A3Audio.DefaultFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
    }
    default A3Audio.Format createAudioFormat(final float sampleRate,
                                             final int sampleSizeInBits,
                                             final int channels,
                                             final boolean signed,
                                             final boolean bigEndian) {
        return new A3Audio.DefaultFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    String[] getAudioReaderFormatNames();
    String[] getAudioWriterFormatNames();

}

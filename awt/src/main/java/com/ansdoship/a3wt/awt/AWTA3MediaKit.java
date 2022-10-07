package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.media.A3Audio;
import com.ansdoship.a3wt.media.A3MediaKit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.FormatConversionProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import static com.ansdoship.a3wt.awt.A3AWTUtils.audioFormat2AWTAudioFormat;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getAudioFileTypeFromExtension;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3MediaKit implements A3MediaKit {

    @Override
    public A3Audio readAudio(final File input) {
        try {
            return new AWTA3Audio(input);
        } catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public A3Audio readAudio(final InputStream input) {
        try {
            final AWTA3Audio result = new AWTA3Audio(input);
            try {
                input.close();
            }
            catch (IOException ignored) {
            }
            return result;
        } catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public A3Audio readAudio(final URL input) {
        try {
            return new AWTA3Audio(input);
        } catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public A3Audio readAudio(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return readAudio(assets.readAsset(input));
    }

    @Override
    public boolean writeAudio(final A3Audio audio, final String formatName, final A3Audio.Format format, final File output) {
        checkArgNotNull(audio, "audio");
        checkArgNotNull(formatName, "formatName");
        checkArgNotNull(format, "format");
        final AudioFileFormat.Type type = getAudioFileTypeFromExtension(formatName);
        if (type == null) return false;
        try (final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFormat2AWTAudioFormat(format), ((AWTA3Audio)audio).getStream())) {
            if (AudioSystem.isFileTypeSupported(type, stream)) {
                AudioSystem.write(stream, type, output);
                return true;
            }
            else return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeAudio(final A3Audio audio, final String formatName, final A3Audio.Format format, final OutputStream output) {
        checkArgNotNull(audio, "audio");
        checkArgNotNull(formatName, "formatName");
        checkArgNotNull(format, "format");
        final AudioFileFormat.Type type = getAudioFileTypeFromExtension(formatName);
        if (type == null) return false;
        try (final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFormat2AWTAudioFormat(format), ((AWTA3Audio)audio).getStream())) {
            if (AudioSystem.isFileTypeSupported(type, stream)) {
                AudioSystem.write(stream, type, output);
                return true;
            }
            else return false;
        } catch (IOException e) {
            return false;
        }
    }

    // I have no better way :(
    @Override
    public String[] getAudioReaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        // JDK Internal Types
        for (AudioFileFormat.Type type : AudioSystem.getAudioFileTypes()) {
            formatNames.add(type.getExtension().toLowerCase());
            if (type.toString().equalsIgnoreCase("aiff")) formatNames.add("aiff");
        }
        // External Types (from SPI)
        ServiceLoader<FormatConversionProvider> serviceLoader = ServiceLoader.load(FormatConversionProvider.class);
        String encodingString;
        for (FormatConversionProvider provider : serviceLoader) {
            for (AudioFormat.Encoding encoding : provider.getSourceEncodings()) {
                if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED) || encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED) ||
                encoding.equals(AudioFormat.Encoding.PCM_FLOAT) || encoding.equals(AudioFormat.Encoding.ALAW) ||
                encoding.equals(AudioFormat.Encoding.ULAW)) continue;
                encodingString = encoding.toString().toLowerCase();
                if (encodingString.contains("vorbis")) formatNames.add("ogg");
                else formatNames.add(encodingString);
            }
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAudioWriterFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (AudioFileFormat.Type type : AudioSystem.getAudioFileTypes()) {
            formatNames.add(type.getExtension().toLowerCase());
            if (type.toString().equalsIgnoreCase("aiff")) formatNames.add("aiff");
        }
        return formatNames.toArray(new String[0]);
    }

}

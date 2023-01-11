package a3wt.awt;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioMixer;
import a3wt.app.A3Assets;
import a3wt.audio.A3AudioKit;
import a3wt.audio.A3Music;
import a3wt.audio.A3Sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.FormatConversionProvider;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3AudioKit implements A3AudioKit {

    protected static AudioInputStream getSupportedAudioInputStream(AudioInputStream ais) {
        if (ais == null) throw new IllegalArgumentException("ais == NULL!");
        if (ais.getFormat().matches(AudioCue.audioFormat)) return ais;
        return new AudioInputStream(AudioSystem.getAudioInputStream(AudioCue.audioFormat, ais), AudioCue.audioFormat, ais.getFrameLength());
    }

    protected static float[] loadAudioInputStream(final AudioInputStream ais) throws IOException {
        int framesCount = 0;
        if (ais.getFrameLength() > Integer.MAX_VALUE >> 1) {
            System.out.println(
                    "WARNING: Clip is too large to entirely fit!");
            framesCount = Integer.MAX_VALUE >> 1;
        }
        else {
            framesCount = (int)ais.getFrameLength();
        }
        // stereo output, so two entries per frame
        float[] temp = new float[framesCount * 2];
        long tempCountdown = temp.length;
        int bytesRead = 0;
        int bufferIdx;
        int clipIdx = 0;
        byte[] buffer = new byte[1024];
        while((bytesRead = ais.read(buffer, 0, 1024)) != -1) {
            bufferIdx = 0;
            for (int i = 0, n = (bytesRead >> 1); i < n; i ++) {
                if ( tempCountdown-- >= 0 ) {
                    temp[clipIdx++] =
                            ( buffer[bufferIdx++] & 0xff )
                                    | ( buffer[bufferIdx++] << 8 ) ;
                }
            }
        }
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i] / 32767f;
        }
        return temp;
    }

    protected static final AudioMixer audioMixer = new AudioMixer();

    @Override
    public A3Sound readSound(final File input) {
        checkArgNotNull(input, "input");
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(input);
            ais = getSupportedAudioInputStream(ais);
            final float[] cue = loadAudioInputStream(ais);
            return new AWTA3Sound(audioMixer, AudioCue.makeStereoCue(cue, input.toURI().toString(), 1));
        }
        catch (final UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public A3Sound readSound(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        try {
            final URL url = assets.getAssetURL(input);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            ais = getSupportedAudioInputStream(ais);
            float[] cue = loadAudioInputStream(ais);
            return new AWTA3Sound(audioMixer, AudioCue.makeStereoCue(cue, url.toString(), 1));
        }
        catch (final UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public A3Music readMusic(final File input) {
        checkArgNotNull(input, "input");
        try {
            return new AWTA3Music(audioMixer, input.toURI().toURL());
        }
        catch (final MalformedURLException e) {
            return null;
        }
    }

    @Override
    public A3Music readMusic(A3Assets assets, String input) {
        return null;
    }

    // I have no better way :(
    @Override
    public String[] getAudioReaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        // JDK
        for (AudioFileFormat.Type type : AudioSystem.getAudioFileTypes()) {
            formatNames.add(type.getExtension().toLowerCase());
            if (type.toString().equalsIgnoreCase("aiff")) formatNames.add("aiff");
        }
        // SPI
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

}

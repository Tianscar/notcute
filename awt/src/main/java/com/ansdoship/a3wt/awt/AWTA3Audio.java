package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.media.A3Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import static com.ansdoship.a3wt.awt.A3AWTUtils.getDecodedAudioInputStream;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getDecodedAudioInputStreamData;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getDecodedAudioInputStreamDataAndClose;
import static com.ansdoship.a3wt.awt.A3AWTUtils.AWTAudioFormat2AudioFormat;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Streams.MAX_BUFFER_SIZE;

public class AWTA3Audio implements A3Audio {

    protected volatile float volume;
    protected volatile float pan;
    protected volatile float speed;
    protected volatile int loops;
    protected volatile long pos;

    protected volatile boolean disposed = false;

    protected final Object source;

    public Object getSource() {
        return source;
    }

    protected volatile Format format;
    protected volatile AudioFormat audioFormat;

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    protected volatile long frameLength;

    protected volatile byte[] md5;

    public boolean matchMD5(final AWTA3Audio audio) {
        checkArgNotNull(audio, "audio");
        return Arrays.equals(md5, audio.md5);
    }

    public AWTA3Audio(final File file) throws UnsupportedAudioFileException, IOException {
        checkArgNotNull(file, "file");
        source = file;
        reset();
        try (final AudioInputStream tmp = mGetStream()) {
            audioFormat = tmp.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            frameLength = tmp.getFrameLength();
        }
    }

    public AWTA3Audio(final URL url) throws UnsupportedAudioFileException, IOException {
        checkArgNotNull(url, "url");
        source = url;
        reset();
        try (final AudioInputStream tmp = mGetStream()) {
            audioFormat = tmp.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            frameLength = tmp.getFrameLength();
        }
    }

    public AWTA3Audio(InputStream stream) throws UnsupportedAudioFileException, IOException {
        checkArgNotNull(stream, "stream");
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(MAX_BUFFER_SIZE);
        source = stream;
        reset();
        try (final AudioInputStream tmp = mGetStream()) {
            audioFormat = tmp.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            frameLength = tmp.getFrameLength();
        }
    }

    public AudioInputStream getStream() {
        try {
            return mGetStream();
        } catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    protected AudioInputStream mGetStream() throws UnsupportedAudioFileException, IOException {
        long fileLength;
        if (source instanceof File) {
            final AudioInputStream result = getDecodedAudioInputStream(AudioSystem.getAudioInputStream((File) source));
            audioFormat = result.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            if (result.getFrameLength() == -1) {
                if (result.markSupported()) {
                    result.mark(MAX_BUFFER_SIZE);
                    final Object[] tuple = getDecodedAudioInputStreamData(result, "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                    result.reset();
                }
                else {
                    final Object[] tuple = getDecodedAudioInputStreamDataAndClose(AudioSystem.getAudioInputStream((File) source), "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                }
                return new AudioInputStream(result, result.getFormat(), fileLength
                                / result.getFormat().getChannels() / result.getFormat().getFrameSize());
            }
            else return result;
        }
        else if (source instanceof URL) {
            final AudioInputStream result = getDecodedAudioInputStream(AudioSystem.getAudioInputStream((URL) source));
            audioFormat = result.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            if (result.getFrameLength() == -1) {
                if (result.markSupported()) {
                    result.mark(MAX_BUFFER_SIZE);
                    final Object[] tuple = getDecodedAudioInputStreamData(result, "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                    result.reset();
                }
                else {
                    final Object[] tuple = getDecodedAudioInputStreamDataAndClose(AudioSystem.getAudioInputStream((URL) source), "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                }
                return new AudioInputStream(result, result.getFormat(), fileLength
                        / result.getFormat().getChannels() / result.getFormat().getFrameSize());
            }
            else return result;
        }
        else if (source instanceof InputStream) {
            ((InputStream) source).reset();
            final AudioInputStream result = getDecodedAudioInputStream(AudioSystem.getAudioInputStream((InputStream) source));
            audioFormat = result.getFormat();
            format = AWTAudioFormat2AudioFormat(audioFormat);
            if (result.getFrameLength() == -1) {
                if (result.markSupported()) {
                    result.mark(MAX_BUFFER_SIZE);
                    final Object[] tuple = getDecodedAudioInputStreamData(result, "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                    result.reset();
                }
                else {
                    final Object[] tuple = getDecodedAudioInputStreamDataAndClose(AudioSystem.getAudioInputStream((InputStream) source), "md5");
                    fileLength = (long) tuple[0];
                    md5 = (byte[]) tuple[1];
                }
                return new AudioInputStream(result, result.getFormat(), fileLength
                        / result.getFormat().getChannels() / result.getFormat().getFrameSize());
            }
            else return result;
        }
        else return null;
    }

    @Override
    public float getFrameRate() {
        return audioFormat.getFrameRate();
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public void setPan(float pan) {
        this.pan = pan;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setLooping(int loops) {
        this.loops = loops;
    }

    @Override
    public void setFramePos(long pos) {
        this.pos = pos;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPan() {
        return pan;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public int getLooping() {
        return loops;
    }

    @Override
    public long getFramePos() {
        return pos;
    }

    @Override
    public long getFrameCount() {
        return frameLength;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (disposed) return;
        disposed = true;
        if (source instanceof InputStream) {
            try {
                ((InputStream) source).close();
            } catch (IOException ignored) {
            }
        }
    }

}

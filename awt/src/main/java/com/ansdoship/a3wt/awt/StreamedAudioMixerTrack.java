package com.ansdoship.a3wt.awt;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioMixer;
import com.adonax.audiocue.AudioMixerTrack;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.readNBytes;

public class StreamedAudioMixerTrack implements AudioMixerTrack {

    private double cursor;
    private double speed;
    private float volume;
    private float pan;

    private double targetSpeed;
    private double targetSpeedIncr;
    private int targetSpeedSteps;

    private float targetVolume;
    private float targetVolumeIncr;
    private int targetVolumeSteps;

    private float targetPan;
    private float targetPanIncr;
    private int targetPanSteps;

    private Function<Float, Float> panL;
    private Function<Float, Float> panR;

    /**
     * Assigns the type of panning to be used.
     *
     * @param panType - a member of the {@code enum AudioCue.PanType}
     * @see AudioCue.PanType
     */
    public void setPanType(AudioCue.PanType panType) {
        panL = panType.left;
        panR = panType.right;
    }

    private volatile boolean trackRunning;
    private volatile AudioMixer audioMixer;
    private volatile float[] cue;
    private volatile int cueFrameLength;
    private volatile float[] readBuffer;
    private final AudioInputStream ais;

    private volatile boolean closed;

    public boolean isClosed() {
        return closed;
    }

    private void checkClosed() {
        if (closed) throw new IllegalStateException("Already closed.");
    }

    private volatile boolean playerRunning;

    public AudioInputStream getAudioInputStream() {
        return ais;
    }

    public StreamedAudioMixerTrack(final AudioInputStream stream) {
        checkArgNotNull(stream, "stream");
        this.ais = AudioCue.getSupportedAudioInputStream(stream);
        playerRunning = false;
        trackRunning = false;
        closed = false;

        cursor = 0;
        speed = 1.4;
        volume = 1;
        pan = 0;
        targetSpeedSteps = 0;
        targetVolumeSteps = 0;
        targetPanSteps = 0;
        // default pan calculation function
        setPanType(AudioCue.PanType.CENTER_LINEAR);
    }

    public void open(final AudioMixer mixer) {
        checkArgNotNull(mixer, "mixer");
        checkClosed();
        if (playerRunning) throw new IllegalStateException("Already opened.");
        playerRunning = true;
        audioMixer = mixer;
        audioMixer.addTrack(this);
        audioMixer.updateTracks();
        readBuffer = new float[audioMixer.sdlByteBufferSize * 2];
        cue = null;
    }

    public void start() {
        checkClosed();
        trackRunning = true;
    }

    public void stop() {
        checkClosed();
        trackRunning = false;
        cue = null;
    }

    public boolean isRunning() {
        return playerRunning;
    }

    public boolean isPlaying() {
        return trackRunning;
    }

    public void close() {
        checkClosed();
        playerRunning = false;
        stop();
        audioMixer.removeTrack(this);
        audioMixer.updateTracks();
        try {
            ais.close();
        } catch (IOException ignored) {
        }
        cue = null;
    }

    @Override
    public boolean isTrackRunning() {
        return trackRunning;
    }

    @Override
    public void setTrackRunning(final boolean trackRunning) {
        throw new UnsupportedOperationException("Cannot manually set whether track running at a StreamedAudioMixerTrack");
    }

    @Override
    public float[] readTrack() throws IOException {
        return fillBuffer(readBuffer);
    }

    /*
     * AudioThread code, executing within the while loop of the run() method.
     */
    private float[] fillBuffer(float[] readBuffer) throws IOException {
        // Start with 0-filled buffer, send out silence
        // if nothing playing.
        int bufferLength = readBuffer.length;
        for (int i = 0; i < bufferLength; i++) {
            readBuffer[i] = 0;
        }

        if (trackRunning) {
            /*
             * Usually, pan won't change, so let's
             * store value and only recalculate when
             * it changes.
             */
            float panFactorL = panL.apply(pan);
            float panFactorR = panR.apply(pan);

            for (int i = 0; i < bufferLength; i += 2) {
            	
                if (cue == null) {
                    cue = new float[(int) (bufferLength * speed)];
                    cueFrameLength = readAudioInputStream(cue, ais);
                }
                
                // adjust volume if needed
                if (targetVolumeSteps-- > 0) {
                    volume += targetVolumeIncr;
                    if (targetVolumeSteps == 0) {
                        volume = targetVolume;
                    }
                }

                // adjust pan if needed
                if (targetPanSteps-- > 0) {
                    if (targetPanSteps != 0) {
                        pan += targetPanIncr;
                    } else {
                        pan = targetPan;
                    }

                    panFactorL = panL.apply(pan);
                    panFactorR = panR.apply(pan);
                }

                // get audioVals, with LERP for fractional cursor position
                float[] audioVals = new float[2];
                if (cursor == (int)cursor) {
                    audioVals[0] = cue[(int)cursor * 2];
                    audioVals[1] = cue[((int)cursor * 2) + 1];
                } else {
                    audioVals = readFractionalFrame(audioVals, cursor);
                }

                readBuffer[i] += (audioVals[0]
                        * volume * panFactorL);
                readBuffer[i + 1] += (audioVals[1]
                        * volume * panFactorR);

                // SET UP FOR NEXT ITERATION
                // adjust pitch if needed
                if (targetSpeedSteps-- > 0) {
                    speed += targetSpeedIncr;
                }

                // set NEXT read position
                cursor += speed;

                // test for "eof" and "looping"
                if (cursor > (cueFrameLength - 1)) {
                    cursor -= cueFrameLength;
                    cueFrameLength = readAudioInputStream(cue, ais);
                    if (cueFrameLength < 0) {
                        close();
                        break;
                    }
                }
                

            }
        }

        return readBuffer;
    }

    /*
     *  Audio thread code, returns a single stereo PCM pair using a
     *  LERP (linear interpolation) function. The difference between
     *  `idx` (floating point value) and `intIndex` determines the
     *  weighting amount for the LERP algorithm. As the PCM array of
     *  audio data is stereo, we use `stereoIndex` (twice the amount
     *  of `intIndex`) to locate the audio values to be weighted.
     */
    private float[] readFractionalFrame(float[] audioVals, double idx) {
        final int intIndex = (int) idx;
        final int stereoIndex = intIndex * 2;

        audioVals[0] = (float)(cue[stereoIndex + 2] * (idx - intIndex)
                + cue[stereoIndex] * ((intIndex + 1) - idx));

        audioVals[1] = (float)(cue[stereoIndex + 3] * (idx - intIndex)
                + cue[stereoIndex + 1] * ((intIndex + 1) - idx));

        return audioVals;
    }
    private static int readAudioInputStream(final float[] cue, final AudioInputStream ais) throws IOException {
        if (ais == null) throw new IllegalArgumentException("ais == NULL!");

        Arrays.fill(cue, 0);

        int bufferIdx;
        int clipIdx = 0;
        int tmp = cue.length / 2;
        final byte[] buffer = new byte[tmp];
        final int bytesRead = readNBytes(ais, buffer, 0, tmp % ais.getFormat().getFrameSize() == 0 ? tmp : tmp - (tmp % ais.getFormat().getFrameSize()));
        if (bytesRead < 0) {
            return bytesRead;
        }
        long tempCountdown = cue.length;
        bufferIdx = 0;
        
        for (int i = 0, n = (bytesRead >> 1); i < n; i ++) {
            if ( tempCountdown-- >= 0) {
                cue[clipIdx++] =
                        ( buffer[bufferIdx++] & 0xff )
                                | ( buffer[bufferIdx++] << 8 ) ;
            }
        }
        for (int i = 0; i < cue.length; i++) {
            cue[i] = cue[i] / 32767f;
        }

        return bytesRead;
    }

}

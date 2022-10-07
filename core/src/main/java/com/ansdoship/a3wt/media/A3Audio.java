package com.ansdoship.a3wt.media;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Disposable;
import com.ansdoship.a3wt.util.A3Math;

import java.util.HashMap;
import java.util.Map;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Audio extends A3Disposable {
    
    class Encoding {
        private Encoding(){}
        /**
         * Specifies signed, linear PCM data.
         */
        public static final String PCM_SIGNED = "PCM_SIGNED";
        /**
         * Specifies unsigned, linear PCM data.
         */
        public static final String PCM_UNSIGNED = "PCM_UNSIGNED";
        /**
         * Specifies floating-point PCM data.
         */
        public static final String PCM_FLOAT = "PCM_FLOAT";
        /**
         * Specifies u-law encoded data.
         */
        public static final String ULAW = "ULAW";
        /**
         * Specifies a-law encoded data.
         */
        public static final String ALAW = "ALAW";
    }

    interface Format extends A3Copyable<Format> {
        void setEncoding(String encoding);
        String getEncoding();
        void setSampleRate(float sampleRate);
        float getSampleRate();
        void setSampleSizeInBits(int sampleSizeInBits);
        int getSampleSizeInBits();
        void setChannels(int channels);
        int getChannels();
        void setFrameSize(int frameSize);
        int getFrameSize();
        void setFrameRate(float frameRate);
        float getFrameRate();
        void setBigEndian(boolean bigEndian);
        boolean isBigEndian();
        Map<String, Object> properties();
        boolean matches(Format format);
    }

    class DefaultFormat implements Format {

        protected boolean bigEndian;
        protected int channels;
        protected String encoding;
        protected float frameRate;
        protected int frameSize;
        protected float sampleRate;
        protected int sampleSizeInBits;

        private final Map<String, Object> properties = new HashMap<>();

        public DefaultFormat(final String encoding,
                             final float sampleRate,
                             final int sampleSizeInBits,
                             final int channels,
                             final int frameSize,
                             final float frameRate,
                             final boolean bigEndian) {
            this(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian, null);
        }

        public DefaultFormat(final String encoding,
                             final float sampleRate,
                             final int sampleSizeInBits,
                             final int channels,
                             final int frameSize,
                             final float frameRate,
                             final boolean bigEndian,
                             final Map<String, Object> properties) {
            checkArgNotNull(encoding, "encoding");
            this.encoding = encoding;
            this.sampleRate = sampleRate;
            this.sampleSizeInBits = sampleSizeInBits;
            this.channels = channels;
            this.frameSize = frameSize;
            this.frameRate = frameRate;
            this.bigEndian = bigEndian;
            if (properties != null) this.properties.putAll(properties);
        }

        public DefaultFormat(final float sampleRate,
                             final int sampleSizeInBits,
                             final int channels,
                             final boolean signed,
                             final boolean bigEndian) {
            this.encoding = signed ? Encoding.PCM_SIGNED : Encoding.PCM_UNSIGNED;
            this.sampleRate = sampleRate;
            this.sampleSizeInBits = sampleSizeInBits;
            this.channels = channels;
            this.frameSize = sampleSizeInBits >> 3;
            if ((sampleSizeInBits & 0x7) != 0) {
                this.frameSize ++;
            }
            this.frameSize *= channels;
            this.frameRate = sampleRate;
            this.bigEndian = bigEndian;
        }

        @Override
        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        @Override
        public String getEncoding() {
            return encoding;
        }

        @Override
        public void setSampleRate(float sampleRate) {
            this.sampleRate = sampleRate;
        }

        @Override
        public float getSampleRate() {
            return sampleRate;
        }

        @Override
        public void setSampleSizeInBits(int sampleSizeInBits) {
            this.sampleSizeInBits = sampleSizeInBits;
        }

        @Override
        public int getSampleSizeInBits() {
            return sampleSizeInBits;
        }

        @Override
        public void setChannels(int channels) {
            this.channels = channels;
        }

        @Override
        public int getChannels() {
            return channels;
        }

        @Override
        public void setFrameSize(int frameSize) {
            this.frameSize = frameSize;
        }

        @Override
        public int getFrameSize() {
            return frameSize;
        }

        @Override
        public void setFrameRate(float frameRate) {
            this.frameRate = frameRate;
        }

        @Override
        public float getFrameRate() {
            return frameRate;
        }

        @Override
        public void setBigEndian(boolean bigEndian) {
            this.bigEndian = bigEndian;
        }

        @Override
        public boolean isBigEndian() {
            return bigEndian;
        }

        @Override
        public Map<String, Object> properties() {
            return properties;
        }

        @Override
        public boolean matches(final Format format) {
            if (format == null) return false;
            if (!encoding.equals(format.getEncoding()) ||
                    channels != format.getChannels() ||
                    sampleSizeInBits != format.getSampleSizeInBits() ||
                    frameSize != format.getFrameSize()) {
                return false;
            }
            if (format.getSampleRate() != -1 &&
                    sampleRate != format.getSampleRate()) {
                return false;
            }
            if (format.getFrameRate() != -1 &&
                    frameRate != format.getFrameRate()) {
                return false;
            }
            if ((sampleSizeInBits > 8)
                    && (bigEndian != format.isBigEndian())) {
                return false;
            }
            return true;
        }

        @Override
        public Format copy() {
            return new DefaultFormat(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian, properties);
        }
        
    }

    float getFrameRate();

    void setVolume(float volume);
    void setPan(float pan);
    void setSpeed(float speed);
    void setLooping(int loops);

    default void reset() {
        setVolume(1.0f);
        setPan(0);
        setSpeed(1.0f);
        setLooping(0);
        setFramePos(0);
    }

    void setFramePos(long pos);
    default void setMillisecondPos(long pos) {
        setFramePos((long) A3Math.clamp((getFrameRate() * pos) / 1_000.0, 0, getFrameCount()));
    }
    default void setFractionalPos(float pos) {
        setFramePos((long) (getFrameCount() * A3Math.clamp(pos, 0, 1)));
    }

    float getVolume();
    float getPan();
    float getSpeed();
    int getLooping();

    long getFramePos();
    default long getMillisecondPos() {
        return (long) (getFramePos() / getFrameRate() * 1_000.0);
    }
    default float getFractionalPos() {
        return (float) (getFramePos() / getFrameCount());
    }

    long getFrameCount();
    default long getMillisecondCount() {
        return (long) (getFrameCount() / getFrameRate() * 1_000.0);
    }

    Format getFormat();

}

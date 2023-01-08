package com.ansdoship.a3wt.android;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.audio.A3AudioKit;
import com.ansdoship.a3wt.audio.A3Music;
import com.ansdoship.a3wt.audio.A3Sound;
import com.ansdoship.a3wt.util.A3Arrays;

import java.io.File;
import java.io.IOException;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3AudioKit implements A3AudioKit {

    protected static final int SOUNDPOOL_MAX_STREAMS = 50;

    protected final SoundPool soundPool;
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(SOUNDPOOL_MAX_STREAMS)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()).build();
        }
        else soundPool = new SoundPool(SOUNDPOOL_MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public A3Sound readSound(final File input) {
        checkArgNotNull(input, "input");
        return new AndroidA3Sound(soundPool, soundPool.load(input.getAbsolutePath(), 1));
    }

    @Override
    public A3Sound readSound(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            return new AndroidA3Sound(soundPool, soundPool.load(((AndroidA3Assets)assets).assets.openFd(input), 1));
        }
        catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Music readMusic(final File input) {
        checkArgNotNull(input, "input");
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(input.getAbsolutePath());
            return new AndroidA3Music(mediaPlayer);
        }
        catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Music readMusic(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(((AndroidA3Assets)assets).assets.openFd(input));
            }
            else {
                final AssetFileDescriptor afd = ((AndroidA3Assets)assets).assets.openFd(input);
                mediaPlayer.setDataSource(afd.getFileDescriptor());
            }
            return new AndroidA3Music(mediaPlayer);
        }
        catch (final IOException e) {
            return null;
        }
    }

    private static final String[] READER_FORMAT_NAMES = new String[] {"wav", "mp3", "ogg", "flac", "3gp", "aac", "midi", "mid"};

    @Override
    public String[] getAudioReaderFormatNames() {
        return A3Arrays.copy(READER_FORMAT_NAMES);
    }

}

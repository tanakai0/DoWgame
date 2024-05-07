package com.example.dowgame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int right_sound, wrong_sound, remainder_time_slow, remainder_time_fast
            ;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(2)
                    .build();

        } else {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        }

        right_sound = soundPool.load(context, R.raw.right, 1);
        wrong_sound = soundPool.load(context, R.raw.wrong, 1);
        remainder_time_slow = soundPool.load(context, R.raw.remainder_time_slow, 1);
        remainder_time_fast = soundPool.load(context, R.raw.remainder_time_fast, 1);

    }

    public void play_right_sound() {
        soundPool.play(right_sound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void play_wrong_sound() {
        soundPool.play(wrong_sound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void play_remainder_time_slow() {
        soundPool.play(remainder_time_slow, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void play_remainder_time_fast() {
        soundPool.play(remainder_time_fast, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}

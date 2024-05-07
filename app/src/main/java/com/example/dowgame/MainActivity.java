package com.example.dowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView view_date_and_time;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BGM  (see hints from https://akira-watson.com/android/audio-player.html)
        audioPlay();

        //get the system date
        Calendar cal = Calendar.getInstance(); // get a calender
        int iYear = cal.get(Calendar.YEAR);
        int iMonth = cal.get(Calendar.MONTH) + 1;
        int iDate = cal.get(Calendar.DATE);
        int iHour = cal.get(Calendar.HOUR_OF_DAY);
        int iMinute = cal.get(Calendar.MINUTE);
        int iSecond = cal.get(Calendar.SECOND);

        String date_and_time = iYear + "年" + iMonth + "月" + iDate + "日"+ iHour + "時" + iMinute + "分" + iSecond + "秒";

        view_date_and_time = findViewById(R.id.date_and_time);
        view_date_and_time.setText(getString(R.string.date_and_time, date_and_time));
    }

    // for BGM
    private boolean audioSetup(){
        boolean fileCheck = false;
        mediaPlayer = MediaPlayer.create(this, R.raw.hmix_gallery_r12);
        // use smartphone button to adjust sound volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        fileCheck = true;
        return fileCheck;
    }

    private void audioPlay() {
        if (mediaPlayer == null) {
            if (audioSetup()){
                Toast.makeText(getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            // when you want to repeat music
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener( mp -> {
            Log.d("debug","end of audio");
            audioStop();
        });
    }

    private void audioStop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void select_mode(View view) {
        audioStop();
        startActivity(new Intent(getApplicationContext(), SelectGameMode.class));
    }

    public void history(View view) {
        audioStop();
        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
    }

    public void tips(View view) {
        audioStop();
        startActivity(new Intent(getApplicationContext(), TipsActivity.class));
    }
}
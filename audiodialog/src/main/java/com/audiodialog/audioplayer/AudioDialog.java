package com.audiodialog.audioplayer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class AudioDialog extends Dialog {

    MediaPlayer mediaPlayer;
    ImageView button;
    SeekBar seekBar;
    Context context;
    String url = "";
    ImageView close;
    private Handler mHandler = new Handler();
    Activity activity;
    int x=0;
    TextView duration;


    public AudioDialog(@NonNull Context context, Activity activity, String audioUrl) {
        super(context);
        this.context = context;
        this.url = url;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audio);

        mediaPlayer = new MediaPlayer();
        button = findViewById(R.id.button);
        close = findViewById(R.id.close);
        seekBar = findViewById(R.id.seekBar);
        duration=findViewById(R.id.duration);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Log.d("Progress changed: ", "" + progress);
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    button.setImageResource(R.drawable.ic_play_arrow_white_24dp);

                } else {

                    if (x==0)
                    {
                        x=1;
                        prepareMediaPlayer();
                    }
                    else{
                        mediaPlayer.start();
                        button.setImageResource(R.drawable.ic_pause_black_24dp);
                    }

                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                dismiss();
            }
        });

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition/3);

                    if (mCurrentPosition<10)
                    {
                        duration.setText("00:0"+mCurrentPosition);
                    }
                    else if (mCurrentPosition>=10 && mCurrentPosition<60)
                    {
                        duration.setText("00:"+mCurrentPosition);
                    }

                    else if ( mCurrentPosition>=60)
                    {
                        int quotient=mCurrentPosition/60;
                        int remainder=mCurrentPosition%60;

                        if (quotient<10)
                        {
                            if (remainder<10) {
                                duration.setText("0" + quotient + ":0" + remainder);
                            }
                            else {
                                duration.setText("0" + quotient + ":" + remainder);
                            }
                        }
                        else {
                            if (remainder<10) {
                                duration.setText( + quotient + ":0" + remainder);
                            }
                            else {
                                duration.setText( + quotient + ":" + remainder);
                            }
                        }

                    }
                }

                mHandler.postDelayed(this, 1000);
            }
        });
    }

    public void prepareMediaPlayer(){
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            button.setImageResource(R.drawable.ic_pause_black_24dp);

        } catch (IOException e) {
            Log.e("", "prepare() failed");
        }
    }

}
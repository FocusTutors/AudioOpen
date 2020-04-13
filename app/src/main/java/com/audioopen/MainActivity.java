package com.audioopen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.audiodialog.audioplayer.AudioDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioDialog audioDialog=new AudioDialog(MainActivity.this,MainActivity.this,"");
        audioDialog.show();
    }
}

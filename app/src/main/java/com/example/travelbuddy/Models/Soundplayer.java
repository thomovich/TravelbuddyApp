package com.example.travelbuddy.Models;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class Soundplayer implements MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaplayer;
    private SeekBar seekbar;
    private Activity activity;
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaplayer = mediaPlayer;
    }
    public Soundplayer(Activity activity, String soundurl){
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void updateseekbar(){
        int currPos = mMediaplayer.getCurrentPosition();
        seekbar.setProgress(currPos);
    }




}

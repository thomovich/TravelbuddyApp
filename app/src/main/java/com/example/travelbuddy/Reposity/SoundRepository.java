package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SoundRepository
{

    public MediaPlayer getMediaplayer(String media){
        MediaPlayer mediaPlayer;
        GetDataFromDb getDataFromDb;
        getDataFromDb = GetDataFromDb.getSingleinstance();
        mediaPlayer = getDataFromDb.getsound(media);
        return mediaPlayer;
    }

}

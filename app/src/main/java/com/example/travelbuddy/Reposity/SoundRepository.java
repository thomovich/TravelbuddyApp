package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SoundRepository
{

    private final MutableLiveData <MediaPlayer> media = new MutableLiveData<MediaPlayer>();


    public void selectMedia(MediaPlayer mediaPlayer){
        media.postValue(mediaPlayer);
    }

    public LiveData<MediaPlayer> getMedia(){
        return media;
    }


    public void getMediaplayer(String media){
        GetDataFromDb getDataFromDb;
        getDataFromDb = GetDataFromDb.getSingleinstance();
        this.media.postValue(getDataFromDb.getsound(media));
    }

}

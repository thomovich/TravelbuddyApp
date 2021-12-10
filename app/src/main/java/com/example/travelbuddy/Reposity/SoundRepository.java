package com.example.travelbuddy.Reposity;

import android.content.Context;
import android.media.MediaDataSource;
import android.media.MediaPlayer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SoundRepository
{
    private static SoundRepository singletonisntance = null;

    private final MutableLiveData <MediaPlayer> media = new MutableLiveData<MediaPlayer>();

    public static SoundRepository getSoundRepositoryInstance(){
        if(singletonisntance == null){
            singletonisntance = new SoundRepository();
        }
        return singletonisntance;
    }

    public void selectMedia(MediaPlayer mediaPlayer){
        media.postValue(mediaPlayer);
    }

    public LiveData<MediaPlayer> getMedia(){
        return media;
    }


    public void getMediaplayer(String media){
        GetDataFromDb getDataFromDb = new GetDataFromDb();
        this.media.postValue(getDataFromDb.getsound(media));

    }

}

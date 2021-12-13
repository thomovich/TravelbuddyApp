package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SoundRepository

{
    private class SoundTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String media = params[0];
            createmedia(media);
            return null;
        }
    }
    private final MutableLiveData<MediaPlayer> media = new MutableLiveData<>();

    public SoundRepository(){

    }

    public void Startdb(String sql){
            SoundTask soundTask = new SoundTask();
            soundTask.execute(sql);
    }


    public LiveData<MediaPlayer> getALlmedia(){
        return media;
    }



    public void createmedia(String media){
        MediaPlayer mediaPlayer;
        GetDataFromDb getDataFromDb;
        getDataFromDb = GetDataFromDb.getSingleinstance();
        mediaPlayer = getDataFromDb.getsound(media);
        this.media.postValue(mediaPlayer);
    }

}

package com.example.travelbuddy.ViewModels;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Reposity.SoundRepository;

public class MainActivityViewModel extends ViewModel {
   private final MutableLiveData<MediaPlayer> song = new MutableLiveData<MediaPlayer>();
    SoundRepository soundRepository = SoundRepository.getSoundRepositoryInstance();
    private final MutableLiveData<Integer> seekbarprgs = new MutableLiveData<Integer>();
    private final MutableLiveData<String> Buttontext = new MutableLiveData<String>();

    public MainActivityViewModel(){
        soundRepository.getMedia().observeForever(new Observer<MediaPlayer>() {
            @Override
            public void onChanged(MediaPlayer mediaPlayer) {
                song.postValue(mediaPlayer);
            }
        });


    }


    public void selectbtntext(String buttontext){

        Buttontext.postValue(buttontext);
    }




    public void selectSong(String song){
        soundRepository.getMediaplayer(song);
    }

    public LiveData<MediaPlayer> getsong(){
        return this.song;
    }



    public void selectseekbar(Integer seekbar){
        seekbarprgs.postValue(seekbar);
    }

    public LiveData<Integer> getSeekbar(){
        return seekbarprgs;
    }


    public LiveData<String> getButtontext(){
        if(Buttontext == null){
            selectbtntext("song is loading");
        } else {
        }
        return Buttontext;
    }




}

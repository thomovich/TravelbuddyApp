package com.example.travelbuddy.ViewModels;

import android.media.MediaPlayer;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    boolean scannedin;
    String btntext;
    String crnsong;
    MediaPlayer song;
    private final MutableLiveData<Integer> seekbarprgs = new MutableLiveData<Integer>();
    private final MutableLiveData<String> Buttontext = new MutableLiveData<String>();
    private final MutableLiveData<String> Currentsong = new MutableLiveData<String>();

    public void selectbtntext(String buttontext){
        btntext = buttontext;
        Buttontext.postValue(buttontext);
    }

    public void selectcurrentsong(String currentsong){
        crnsong = currentsong;
        Currentsong.postValue(currentsong);
    }

    public void selectseekbar(Integer seekbar){
        seekbarprgs.postValue(seekbar);
    }

    public LiveData<Integer> getSeekbar(){
        return seekbarprgs;
    }

    public LiveData<String> getCurrentsong(){
        return Currentsong;
    }

    public LiveData<String> getButtontext(){
        if(Buttontext == null){
            selectbtntext("song is loading");
        } else {
        }
        return Buttontext;
    }

    public boolean getscannedin(){
        return scannedin;
    }
    public String getBtntext(){
        return btntext;
    }

    public String getCrnsong(){
        return crnsong;
    }

    public void setSong(MediaPlayer mediaPlayer){
        song = mediaPlayer;
    }

    public MediaPlayer getSong(){
        return song;
    }


}

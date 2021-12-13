package com.example.travelbuddy.Reposity;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelbuddy.Models.Sights;

import java.util.ArrayList;
import java.util.List;

public class SightRepository {

    private ArrayList<Sights> sightList = new ArrayList<>();
    private final MutableLiveData<List<Sights>> Sights = new MutableLiveData<>();


    //bruger en async task for at tage lidt af presset fra UI thread
    //Så undgår man at programmet halter imens det venter på dbs'en
    private class MyTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int sql = params[0];
            createlist(sql);
            return null;
        }
    }

    public SightRepository() {
    }

    public void startdbtask(int sql){
        new MyTask().execute(sql);
    }

    public void createlist(int sql){
        try{
            if(Sights.getValue().isEmpty());
            //laver check for at undgå unødvendige dbs kald
        } catch (NullPointerException e){
            GetDataFromDb getDataFromDb = GetDataFromDb.getSingleinstance();
            sightList = getDataFromDb.getSights(sql);
            Sights.postValue(sightList);
            Log.d("kalder dbs", "tag");
        }



    }

    public LiveData<List<Sights>> getAllSights() {

        return Sights;

    }

    public void selectsights(List<Sights> sights){
        this.Sights.postValue(sights);
    }

    public ArrayList<Sights> getSightList(){
        return sightList;
    }
}


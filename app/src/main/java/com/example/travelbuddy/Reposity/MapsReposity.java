package com.example.travelbuddy.Reposity;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelbuddy.Models.GlobalVariable;
import com.example.travelbuddy.Models.LanguageVariant;
import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsReposity {

    private int qrCode;
    private GetDataFromDb dblookups = new GetDataFromDb();
    private ArrayList<Sight> sights = new ArrayList<>();
    private ArrayList<Sight> returnsights = new ArrayList<>();
    private ArrayList<LanguageVariant> variants = new ArrayList<>();
    private ArrayList<MarkerOptions> markers = new ArrayList<>();
    private static MapsReposity singletonisntance = null;

    public static MapsReposity getMapsRepositoryInstance(){
        if(singletonisntance == null){
            singletonisntance = new MapsReposity();
        }
        return singletonisntance;
    }

    //starter
    private ArrayList<Sight> sightList = new ArrayList<>();
    private final MutableLiveData<List<Sight>> Sights = new MutableLiveData<>();


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


    public void startdbtask(int sql){
        new MapsReposity.MyTask().execute(sql);
    }

    public void createlist(int sql){
        try{
            if(Sights.getValue().isEmpty());
            //laver check for at undgå unødvendige dbs kald
        } catch (NullPointerException e){
            GetDataFromDb getDataFromDb = GetDataFromDb.getSingleinstance();
            sightList = getDataFromDb.getcoord(sql);
            Sights.postValue(sightList);
            Log.d("kalder dbs fra maps", "tag");
        }



    }

    public LiveData<List<Sight>> getAllSights() {

        return Sights;

    }



    public ArrayList<Sight> getLocations(){

        qrCode = GlobalVariable.getInstance().qrcode;

        //Db get sights per qrCode code here plus language variant variables


        //sights=dblookups.getSights(qrCode);

        ArrayList<Sight> sightsToMap;
        sightsToMap = dblookups.getcoord(qrCode);





        return sightsToMap;
    }
}

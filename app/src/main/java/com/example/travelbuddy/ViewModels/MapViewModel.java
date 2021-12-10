package com.example.travelbuddy.ViewModels;


import android.content.Context;
import android.media.MediaPlayer;

import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Reposity.MapsReposity;
import com.example.travelbuddy.Reposity.SoundRepository;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {

    static MapsReposity repo;



    public MapViewModel(){

    if(repo==null){
        repo = MapsReposity.getMapsRepositoryInstance();
    }

}

    public ArrayList<MarkerOptions> getMarkerLocation() {

        return repo.getLocations();
    }

}
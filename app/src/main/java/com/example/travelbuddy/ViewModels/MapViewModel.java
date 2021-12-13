package com.example.travelbuddy.ViewModels;



import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Reposity.MapsReposity;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {

    static MapsReposity repo;



    public MapViewModel(){

    if(repo==null){
        repo = MapsReposity.getMapsRepositoryInstance();
    }

}

    public ArrayList<Sight> getMarkerLocation() {

        return repo.getLocations();
    }

}
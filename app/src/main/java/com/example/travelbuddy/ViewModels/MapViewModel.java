package com.example.travelbuddy.ViewModels;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.Reposity.MapsReposity;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {

    static MapsReposity repo;
    private final MutableLiveData<List<Sight>> Sight = new MutableLiveData<List<Sight>>();



    public MapViewModel(){

    if(repo==null){
        repo = MapsReposity.getMapsRepositoryInstance();
    }

}

    public ArrayList<Sight> getMarkerLocation() {

        return repo.getLocations();
    }

    public void createdata(int sql){
        repo.startdbtask(sql);
    }

    public LiveData<List<Sight>> getSights(){
        return repo.getAllSights();
    }

}
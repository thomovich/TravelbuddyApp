package com.example.travelbuddy.Reposity;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsReposity {

    private static MapsReposity singletonisntance = null;

    public static MapsReposity getMapsRepositoryInstance(){
        if(singletonisntance == null){
            singletonisntance = new MapsReposity();
        }
        return singletonisntance;
    }

    public ArrayList<MarkerOptions> getLocations(){
        return null;
    }
}

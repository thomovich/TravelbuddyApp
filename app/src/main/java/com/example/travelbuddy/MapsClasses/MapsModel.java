package com.example.travelbuddy.MapsClasses;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsModel implements IMapsModel{

    ArrayList<MarkerOptions> markers = new ArrayList<>();

    @Override
    public ArrayList<MarkerOptions> getMarkerLocation() {
        for(int i=0;i < 5;i++){
            MarkerOptions marker = new MarkerOptions();
            LatLng latLng = new LatLng(56.1582, 10.1920);
            marker.position(latLng)
                    .title("something from db");
            markers.add(marker);
        }
        return markers;
    }
}

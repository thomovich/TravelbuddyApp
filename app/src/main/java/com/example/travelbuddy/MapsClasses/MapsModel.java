package com.example.travelbuddy.MapsClasses;

import android.graphics.Color;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsModel implements IMapsModel{

    ArrayList<MarkerOptions> markers = new ArrayList<>();

    @Override
    public ArrayList<MarkerOptions> getMarkerLocation() {
        for(int i=0;i < 5;i++){
            MarkerOptions marker = new MarkerOptions();
            LatLng latLng = new LatLng(55.1562, 10.1920);
            marker.position(latLng)
                    .title("something from db");
            markers.add(marker);
        }
        return markers;
    }
}

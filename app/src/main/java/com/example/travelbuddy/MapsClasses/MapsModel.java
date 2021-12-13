package com.example.travelbuddy.MapsClasses;

import com.example.travelbuddy.Models.GlobalVariable;
import com.example.travelbuddy.Models.Sight;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsModel implements IMapsModel{

    ArrayList<MarkerOptions> markers = new ArrayList<>();

    ArrayList<Sight> sights = new ArrayList<>();

    private String qrCode;


    @Override
    public ArrayList<MarkerOptions> getMarkerLocation() {

       qrCode= GlobalVariable.getInstance().qrcode;

        for(int i=0;i < sights.size();i++){

            MarkerOptions marker = new MarkerOptions();
            LatLng latLng = new LatLng(56.1582, 10.1920);
            marker.position(latLng)
                    .title("something from db");
            markers.add(marker);
        }
        return markers;
    }
}

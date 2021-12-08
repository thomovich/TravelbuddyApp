package com.example.travelbuddy.MapsClasses;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public interface IMapsModel {

    ArrayList<MarkerOptions> getMarkerLocation();
}

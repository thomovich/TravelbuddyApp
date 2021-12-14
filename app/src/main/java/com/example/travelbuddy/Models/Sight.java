package com.example.travelbuddy.Models;


import android.media.Image;

import com.example.travelbuddy.R;
import com.google.android.gms.maps.model.BitmapDescriptor;

public class Sight {


    private double Lat=56.505966;
    private double Long=9.717303;
    private int radius=5;
    private int image = R.drawable.asbjorn;
    private LanguageVariant languageVariant;

    public Sight(double lat, double aLong, int radius, int image, LanguageVariant languageVariant) {

        Lat = lat;
        Long = aLong;
        this.radius = radius;
        this.image = image;
        this.languageVariant = languageVariant;
    }


    public double getLat() {
        return Lat;
    }

    public double getLong() {
        return Long;
    }

    public int getRadius() {
        return radius;
    }

    public int getImage() {
        return image;
    }

    public LanguageVariant  getLanguageVariant(){
        return languageVariant;
    }


}

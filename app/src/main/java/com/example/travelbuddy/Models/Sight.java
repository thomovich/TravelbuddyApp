package com.example.travelbuddy.Models;


import android.media.Image;

import com.example.travelbuddy.R;
import com.google.android.gms.maps.model.BitmapDescriptor;

public class Sight {

    private int id;
    private double Lat;
    private double Long;
    private int radius;
    private byte[] image;
    private LanguageVariant languageVariant;

    public Sight(int id,double lat, double aLong, int radius, byte[] image, LanguageVariant languageVariant) {

        this.id = id;
        Lat = lat;
        Long = aLong;
        this.radius = radius;
        this.image = image;
        this.languageVariant = languageVariant;
    }

    public int getId() {
        return id;
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

    public byte[] getImage() {
        return image;
    }

    public LanguageVariant  getLanguageVariant(){
        return languageVariant;
    }


}

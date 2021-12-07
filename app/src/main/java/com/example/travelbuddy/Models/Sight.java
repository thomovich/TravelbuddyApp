package com.example.travelbuddy.Models;

import android.media.Image;

import com.example.travelbuddy.R;

public class Sight {

    private int Id=1;
    private int TourId=1;
    private double Lat=56.505966;
    private double Long=9.717303;
    private int radius=5;
    private int image = R.drawable.asbjorn;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTourId() {
        return TourId;
    }

    public void setTourId(int tourId) {
        TourId = tourId;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

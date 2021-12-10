package com.example.travelbuddy.Models;

public class Sights {

    private String sName;
    private String detailedinfo;
    private int img;

    public Sights(String name, int Id, String detailedinfo) {
        this.sName = name;
        this.img = Id;
        this.detailedinfo = detailedinfo;
    }

    public String getName() {
        return sName;
    }

    public int getImg() {
        return img;
    }

    public String getDetailedinfo(){
        return detailedinfo;
    }
}


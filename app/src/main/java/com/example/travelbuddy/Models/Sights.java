package com.example.travelbuddy.Models;

public class Sights {

    private String sName;
    private String detailedinfo;
    private byte [] imgbase64;

    public Sights(String name, byte[] imgstring, String detailedinfo) {
        this.sName = name;
        this.imgbase64 = imgstring;
        this.detailedinfo = detailedinfo;
    }

    public String getName() {
        return sName;
    }

    public byte[] getImg() {
        return imgbase64;
    }

    public String getDetailedinfo(){
        return detailedinfo;
    }
}


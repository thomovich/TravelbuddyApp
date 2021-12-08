package com.example.travelbuddy.Models;

public class Sights {

    private String sName;
    private int sId;

    public Sights(String name, int Id) {
        sName = name;
        sId = Id;
    }

    public String getName() {
        return sName;
    }

    public int getId() {
        return sId;
    }
}


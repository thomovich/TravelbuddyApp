package com.example.travelbuddy.Models;

public class About {

    private String aName;
    private int aId;

    public About(String name, int Id) {
        aName = name;
        aId = Id;
    }

    public String getName() {
        return aName;
    }

    public int getId() {
        return aId;
    }
}


package com.example.travelbuddy.Models;

public class LanguageVariant {

    private int sightId;

    private int lanCode;

    private String name;

    private String description;

    private int sightAudio;

    public LanguageVariant(int sightId, int lanCode, String name, String description, int sightAudio) {
        this.sightId = sightId;
        this.lanCode = lanCode;
        this.name = name;
        this.description = description;
        this.sightAudio = sightAudio;
    }

    public int getSightId() {
        return sightId;
    }

    public int getLanCode() {
        return lanCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSightAudio() {
        return sightAudio;
    }

}

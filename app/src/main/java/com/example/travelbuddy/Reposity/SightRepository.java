package com.example.travelbuddy.Reposity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class SightRepository {

    private ArrayList<Sights> sightList = new ArrayList<>();
    private final MutableLiveData<List<Sights>> Sights = new MutableLiveData<>();


    public SightRepository() {

        sightList.add(new Sights("Møllestien", R.drawable.mollestien));
        sightList.add(new Sights("Marselisborg slot", R.drawable.slot));
        sightList.add(new Sights("Agnete og Havmanden", R.drawable.agnete));
        sightList.add(new Sights("Den svangre", R.drawable.densvangre));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen));


        Sights.setValue(sightList);
    }

    public LiveData<List<Sights>> getAllSights() {

        return Sights;

    }
}


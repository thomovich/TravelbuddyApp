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

        sightList.add(new Sights("Drinking Coffee", R.drawable.thomas));
        sightList.add(new Sights("Getting a Beer", R.drawable.frederik));
        sightList.add(new Sights("Taking a Walk", R.drawable.rasmus));
        sightList.add(new Sights("Enjoying the Sun", R.drawable.asbjorn));
        sightList.add(new Sights("Having a good time with friends", R.drawable.simon));


        Sights.setValue(sightList);
    }

    public LiveData<List<Sights>> getAllSights() {

        return Sights;

    }
}


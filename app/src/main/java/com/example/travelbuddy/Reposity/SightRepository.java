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

        sightList.add(new Sights("Møllestien", R.drawable.mollestien, "Møllestien was created in 1915 and showcases some of the most beautiful houses in Århus.:"));
        sightList.add(new Sights("Marselisborg slot", R.drawable.slot, "Marselisborg Slot is the place the royal family spend some of their holidays and vacations: "));
        sightList.add(new Sights("Agnete og Havmanden", R.drawable.agnete, "Beautiful spring in the center of Århus! Student often spend time bathing in its waters: "));
        sightList.add(new Sights("Den svangre", R.drawable.densvangre, "Created as a gift for Århus, this statue, is located near the trainstation in a little park: "));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen, "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen, "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen, "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen, "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", R.drawable.flodhesteungen, "This statue is great for kids, since the stones surrounding it are met for climbing: "));

        Sights.setValue(sightList);
    }

    public LiveData<List<Sights>> getAllSights() {

        return Sights;

    }

    public ArrayList<Sights> getSightList(){
        return sightList;
    }
}


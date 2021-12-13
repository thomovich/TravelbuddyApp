package com.example.travelbuddy.Reposity;

import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.load.engine.Resource;
import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SightRepository {

    private ArrayList<Sights> sightList = new ArrayList<>();
    private final MutableLiveData<List<Sights>> Sights = new MutableLiveData<>();



    public SightRepository(String sql) {
        GetDataFromDb getDataFromDb = GetDataFromDb.getSingleinstance();

        String img = "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";
        final byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
        sightList.add(new Sights("base64test",decodedBytes,"test"));
        /*sightList.add(new Sights("Møllestien", "R.drawable.mollestien", "Møllestien was created in 1915 and showcases some of the most beautiful houses in Århus.:"));
        sightList.add(new Sights("Marselisborg slot", "R.drawable.slot", "Marselisborg Slot is the place the royal family spend some of their holidays and vacations: "));
        sightList.add(new Sights("Agnete og Havmanden", "R.drawable.agnete", "Beautiful spring in the center of Århus! Student often spend time bathing in its waters: "));
        sightList.add(new Sights("Den svangre", "R.drawable.densvangre", "Created as a gift for Århus, this statue, is located near the trainstation in a little park: "));
        sightList.add(new Sights("Flodhesteungen", "R.drawable.flodhesteungen", "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", "R.drawable.flodhesteungen", "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", "R.drawable.flodhesteungen", "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", "R.drawable.flodhesteungen", "This statue is great for kids, since the stones surrounding it are met for climbing: "));
        sightList.add(new Sights("Flodhesteungen", "R.drawable.flodhesteungen", "This statue is great for kids, since the stones surrounding it are met for climbing: "));
*/
        Sights.setValue(sightList);
    }

    public LiveData<List<Sights>> getAllSights() {

        return Sights;

    }

    public ArrayList<Sights> getSightList(){
        return sightList;
    }
}


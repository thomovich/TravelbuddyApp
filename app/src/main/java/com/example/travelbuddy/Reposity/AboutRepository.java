package com.example.travelbuddy.Reposity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelbuddy.Models.About;
import com.example.travelbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class AboutRepository {

    private ArrayList<About> aboutList = new ArrayList<>();
    private final MutableLiveData<List<About>> Abouts = new MutableLiveData<>();


    public AboutRepository() {

        aboutList.add(new About("Drinking Coffee", R.drawable.asbjorn));
        aboutList.add(new About("Getting a Beer", R.drawable.frederik));
        aboutList.add(new About("Taking a Walk", R.drawable.simon));
        aboutList.add(new About("Enjoying the Sun", R.drawable.rasmus));
        aboutList.add(new About("Having a good time with friends", R.drawable.thomas));


        Abouts.setValue(aboutList);
    }

    public LiveData<List<About>> getAllAbouts() {

        return Abouts;

    }
}


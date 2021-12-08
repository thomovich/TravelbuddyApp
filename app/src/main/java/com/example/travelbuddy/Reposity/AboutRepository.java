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

        aboutList.add(new About("This is Asbjørn. He is 24 years old and is our lead designer", R.drawable.asbjorn));
        aboutList.add(new About("This is Frederik. He is 26 years old and work in the company as the CE", R.drawable.frederik));
        aboutList.add(new About("This is Simon. He is 22 years old, and work as CEO", R.drawable.simon));
        aboutList.add(new About("This is rasmus. He is 25 years old. He is in charge of all pickles.", R.drawable.rasmus));
        aboutList.add(new About("This is Thomas. He is head og the janitors in the company", R.drawable.thomas));


        Abouts.setValue(aboutList);
    }

    public LiveData<List<About>> getAllAbouts() {

        return Abouts;

    }
}


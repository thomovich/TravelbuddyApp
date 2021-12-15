package com.example.travelbuddy.Reposity;


import android.content.Context;
import android.content.res.Resources;

import com.bumptech.glide.load.engine.Resource;
import com.example.travelbuddy.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.travelbuddy.Models.About;
import com.example.travelbuddy.Styling.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AboutRepository {

    private ArrayList<About> aboutList = new ArrayList<>();
    private final MutableLiveData<List<About>> Abouts = new MutableLiveData<>();

    public AboutRepository() {

        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_asbjorn), R.drawable.asbjorn));
        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_frederik), R.drawable.frederik));
        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_simon), R.drawable.simon));
        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_ralle), R.drawable.rasmus));
        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_thomas), R.drawable.thomas));
        aboutList.add(new About(App.getContext().getResources().getString(R.string.text_info), R.drawable.travelbuddyfinal));

        Abouts.setValue(aboutList);

    }

    public LiveData<List<About>> getAllAbouts() {

        return Abouts;

    }
}


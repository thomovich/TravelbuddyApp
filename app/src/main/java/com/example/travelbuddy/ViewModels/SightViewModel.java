package com.example.travelbuddy.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.Reposity.SightRepository;

import java.util.List;

public class SightViewModel extends ViewModel {

    SightRepository repository = new SightRepository();


    public LiveData<List<Sights>> getAllSights() {

        return repository.getAllSights();
    }


}


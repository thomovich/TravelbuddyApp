package com.example.travelbuddy.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Models.About;
import com.example.travelbuddy.Reposity.AboutRepository;

import java.util.List;

public class AboutViewModel extends ViewModel {

    AboutRepository repository = new AboutRepository();

    public LiveData<List<About>> getAllAbouts() {

        return repository.getAllAbouts();
    }
}

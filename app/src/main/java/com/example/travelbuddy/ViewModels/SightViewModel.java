package com.example.travelbuddy.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.Reposity.SightRepository;

import java.util.ArrayList;
import java.util.List;

public class SightViewModel extends ViewModel {
    int posindex;
    int topview;
    private final MutableLiveData<Sights> Sights = new MutableLiveData<Sights>();

    public void selectSights(Sights sights){

        Sights.setValue(sights);
    }

    SightRepository repository = new SightRepository("");

    public LiveData<List<Sights>> getAllSights() {

        return repository.getAllSights();
    }



    public LiveData<Sights> getSights(){
        return Sights;
    }

    public int getPosindex(){
        return posindex;
    }

    public int getTopview(){
        return topview;
    }

    public void setOffsets(int posindex, int topview){
        this.posindex = posindex;
        this.topview = topview;
    }


}


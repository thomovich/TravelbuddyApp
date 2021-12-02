package com.example.travelbuddy.ViewModels;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityViewModel extends ViewModel {
    boolean scannedin;
    ViewModel sharedview;

    public boolean getscannedin(){
        return scannedin;
    }



}

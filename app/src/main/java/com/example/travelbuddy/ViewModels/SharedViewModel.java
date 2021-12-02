package com.example.travelbuddy.ViewModels;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    public boolean qrscanned;

    public void setQrscanned(boolean qrscanned){
        this.qrscanned = qrscanned;
    }
    public boolean getQrscanned(){
        return qrscanned;
    }
}

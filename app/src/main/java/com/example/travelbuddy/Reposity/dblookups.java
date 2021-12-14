package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;

import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;

import java.util.ArrayList;

public interface dblookups {
    boolean checkqr (String Qrcode);
    ArrayList<Sight> getcoord(int Qrcode);
    MediaPlayer getsound(String Qrcode);
    ArrayList<Sights> getSights(int Qrcode);
}

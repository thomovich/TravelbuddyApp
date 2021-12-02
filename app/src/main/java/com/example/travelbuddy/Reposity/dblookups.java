package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;

import java.util.ArrayList;

public interface dblookups {
    public boolean checkqr (String Qrcode);
    public ArrayList<String> getcoord(String Qrcode);
    public ArrayList<MediaPlayer> getsound(String Qrcode);
}

package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;

import java.util.ArrayList;

public interface dblookups {
    boolean checkqr (String Qrcode);
    ArrayList<String> getcoord(String Qrcode);
    MediaPlayer getsound(String Qrcode);
}

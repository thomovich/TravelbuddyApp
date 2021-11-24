package com.example.travelbuddy.MapsClasses;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;

public class GetNearbyItems extends AsyncTask<Object, String, String> {


    String googlePlacesData;
    GoogleMap map;
    String url;
    @Override
    protected String doInBackground(Object... objects) {

        map = (GoogleMap) objects[0];
        url =(String) objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readURl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

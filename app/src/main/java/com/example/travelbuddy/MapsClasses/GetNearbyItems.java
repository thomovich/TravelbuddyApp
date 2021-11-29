package com.example.travelbuddy.MapsClasses;

import android.os.AsyncTask;
import android.telephony.CarrierConfigManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
        List<HashMap<String,String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyItems(nearbyPlaceList);
        super.onPostExecute(s);
    }

    private void showNearbyItems(List<HashMap<String,String>> nearbyItemList){
        for(int i=0; i<nearbyItemList.size() ; i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> itemsPlace = nearbyItemList.get(i);

            String itemName = itemsPlace.get("item_name");
            Double lat = Double.parseDouble(itemsPlace.get("lat"));
            Double lng = Double.parseDouble(itemsPlace.get("lng"));

            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(itemName);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }
}

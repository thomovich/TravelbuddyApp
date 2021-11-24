package com.example.travelbuddy.MapsClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getItems(JSONObject travelBuddyPlaces)
    {

        HashMap<String, String> travelBuddyMap = new HashMap<>();
        String itemName = "-NA-";
        String latitude = "";
        String longitude = "";


        try{
            if(!travelBuddyPlaces.isNull("itemName"))
            {
                try {
                    itemName = travelBuddyPlaces.getString("itemName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            try {
                latitude = travelBuddyPlaces.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = travelBuddyPlaces.getJSONObject("geometry").getJSONObject("location").getString("lng");

                travelBuddyMap.put("itemName",itemName);
                travelBuddyMap.put("latitude",latitude);
                travelBuddyMap.put("longitude",longitude);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            }
        catch (Exception e){
            e.printStackTrace();
        }
        return travelBuddyMap;
    }
    private List<HashMap<String, String>> getItems(JSONArray array){
        int count = array.length();
        List<HashMap<String, String>> itemMap = null;

        for(int i = 0; i<count; i++){
            try {
                placeMap = getItems((JSONObject) array.get(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

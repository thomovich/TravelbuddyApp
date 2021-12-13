package com.example.travelbuddy.Reposity;

import android.util.Pair;

import com.example.travelbuddy.Models.GlobalVariable;
import com.example.travelbuddy.Models.LanguageVariant;
import com.example.travelbuddy.Models.Sight;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsReposity {

    private String qrCode;
    private dblookups dblookups;
    private ArrayList<Sight> sights = new ArrayList<>();
    private ArrayList<Sight> returnsights = new ArrayList<>();
    private ArrayList<LanguageVariant> variants = new ArrayList<>();
    private ArrayList<MarkerOptions> markers = new ArrayList<>();
    private static MapsReposity singletonisntance = null;

    public static MapsReposity getMapsRepositoryInstance(){
        if(singletonisntance == null){
            singletonisntance = new MapsReposity();
        }
        return singletonisntance;
    }



    public ArrayList<Sight> getLocations(){

        qrCode = GlobalVariable.getInstance().qrcode;

        //Db get sights per qrCode code here plus language variant variables


        //sights=dblookups.getSights(qrCode);

        variants = null;

        for(int i = 0; i<1;i++){


            Sight sight = new Sight(
                    sights.get(i).getId(),
                    sights.get(i).getTourId(),
                    sights.get(i).getLat(),
                    sights.get(i).getLong(),
                    sights.get(i).getRadius(),
                    sights.get(i).getImage(),
                     new LanguageVariant(
                    variants.get(i).getSightId(),
                    variants.get(i).getLanCode(),
                    variants.get(i).getName(),
                    variants.get(i).getDescription(),
                    variants.get(i).getSightAudio()));
            returnsights.add(sight);
        }
        /*Sight sight = new Sight(
                    1,
                    1,
                    56.1582,
                    10.1920,
                    1000,
                    R.drawable.asbjorn,
                     new LanguageVariant(
                    1,
                    1,
                    "agnete og havmand",
                    "dsasa",
                    123));*/



        return returnsights;
    }
}

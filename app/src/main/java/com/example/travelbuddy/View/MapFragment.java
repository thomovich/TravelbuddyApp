package com.example.travelbuddy.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelbuddy.Main.MainActivity;
import com.example.travelbuddy.MapsClasses.IMapsModel;
import com.example.travelbuddy.MapsClasses.MapsModel;
import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.R;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class MapFragment extends Fragment{

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private GoogleMap googleMap;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    IMapsModel imap;
    MapView mMapView;
    Sight sights = new Sight();

    Circle circle;
    ArrayList<LatLng> locationList = new ArrayList<>();
    ArrayList<Circle> cirleList=new ArrayList<>();
    ArrayList<MarkerOptions> markerOptions;
    ArrayList<CircleOptions> radiusContainer=new ArrayList<>();

    //google's API for location services. Majority of the app functions using this class.
    FusedLocationProviderClient fusedLocationProviderClient;

    //location request is a config file for all settings related to FusedLocationProvider
    LocationRequest locationRequest;

    //location callback is the callback that is triggered when the interval ends

    LocationCallback callback;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mMapView.onResume();


        mMapView.getMapAsync(new OnMapReadyCallback() {

            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                checkpermission();
                //has to run before doing any coding

                //set properties for locationRequest
                locationRequest = new LocationRequest();
                locationRequest.setInterval(3000* DEFAULT_UPDATE_INTERVAL);
                locationRequest.setFastestInterval(3000* FAST_UPDATE_INTERVAL);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



                //triggers when interval is triggered
                callback = new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        checkLocationToMarker(locationResult.getLastLocation());
                        super.onLocationResult(locationResult);
                    }
                };
                checkGPS();
                startLocationUpdates();



                //markers to explore

                    LatLng arhus = new LatLng(56.1562, 10.1920);

                //zoom to current location
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(arhus)
                        .zoom(12)
                        .build();


                googleMap.setMyLocationEnabled(true);


                 markerOptions = new MapsModel().getMarkerLocation();

                for(int i=0; i<markerOptions.size();i++){
                    CircleOptions circly = new CircleOptions()
                            .center(markerOptions.get(i).getPosition())
                            .radius(10000)
                            .fillColor(Color.BLUE);
                    radiusContainer.add(circly);
                    googleMap.addMarker(markerOptions.get(i));
                    googleMap.addCircle(circly);
                }




                //virker ikke
                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

                    @Override
                    public boolean onMyLocationButtonClick() {
                        //when clicked turn to this location
                        return true;
                    }


                });


            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,callback,null);

    }

    @SuppressLint("MissingPermission")
    private void checkGPS() {

        //get current location from the fused client
        //update UI
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


    }

    private void checkpermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED  ){
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return ;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        mMapView = rootView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void checkLocationToMarker(Location location){

for(int i = 0; i<1;i++){

    float[] distance = new float[1];
    Location.distanceBetween( location.getLatitude(),location.getLongitude(),
            markerOptions.get(i).getPosition().latitude, markerOptions.get(i).getPosition().longitude,distance);

    if( distance[0] > radiusContainer.get(i).getRadius()  ){
        Toast.makeText(getActivity().getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
    } else {
        playAudio();
        Toast.makeText(getActivity().getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
    }
}



    }

    private void playAudio() {

    }


}
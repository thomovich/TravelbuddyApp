package com.example.travelbuddy.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelbuddy.Main.MainActivity;
import com.example.travelbuddy.ViewModels.MapViewModel;
import com.example.travelbuddy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private MapViewModel mViewModel;

    private GoogleMap googleMap;
    private MapViewModel model;
    SupportMapFragment supportMapFragment;
    MapView mMapView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        checkPermission();


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        // mMapView = rootView.findViewById(R.id.mapView);
        //mMapView.onCreate(savedInstanceState);

        return rootView;
    }

    private void checkPermission() {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                /*
                try {
                    MapsInitializer.initialize(getActivity().getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager mapFragment=  getActivity().getSupportFragmentManager();
                supportMapFragment = (SupportMapFragment) mapFragment.findFragmentById(R.id.mapView);
                if (supportMapFragment == null) {
                    supportMapFragment = SupportMapFragment.newInstance();
                    mapFragment.beginTransaction().replace(R.id.mapView, supportMapFragment).commit();
                }

                supportMapFragment.getMapAsync(this);*/

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                Uri  uri =Uri.fromParts("package",getActivity().getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);*/

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

    /*@Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }*/

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

            /*googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    googleMap.setMyLocationEnabled(true);
                    return true;
                }
            });
            googleMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) getActivity());*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        googleMap.setMyLocationEnabled(true);


        }
    }

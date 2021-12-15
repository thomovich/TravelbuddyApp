package com.example.travelbuddy.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.travelbuddy.Listener.OnMapsEnterListener;
import com.example.travelbuddy.MapsClasses.IMapsModel;
import com.example.travelbuddy.MapsClasses.MapsModel;
import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.R;

import com.example.travelbuddy.ViewModels.MapViewModel;
import com.example.travelbuddy.ViewModels.SightViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment {

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private GoogleMap googleMap;
    private OnMapsEnterListener onMapsEnterListener;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    IMapsModel imap;



    MapViewModel viewModel = null;
    Location currentLocation = null;
    List<Integer> ids = new ArrayList<>();
    ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
    ArrayList<CircleOptions> radiusContainer = new ArrayList<>();

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
                locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
                locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


                //triggers when interval is triggered
                callback = new LocationCallback() {
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
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                googleMap.setOnMyLocationButtonClickListener(() -> {
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                            .zoom(15)
                            .build()));
                    return true;
                });

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setTiltGesturesEnabled(true);
                mapView = getView();


                //marker
                viewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
                viewModel.createdata(2);
                viewModel.getSights().observe(getViewLifecycleOwner(),sights->{


                    for(int i=0;i < sights.size();i++){
                        ids.add(sights.get(i).getId());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(sights.get(i).getImage(),0,sights.get(i).getImage().length);
                        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,150,150,false);
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap1);

                        MarkerOptions marker = new MarkerOptions();
                        LatLng latLng = new LatLng(sights.get(i).getLat(), sights.get(i).getLong());
                        marker.position(latLng).icon(bitmapDescriptor)
                                .title(sights.get(i).getLanguageVariant().getName());
                        markerOptions.add(marker);

                        CircleOptions circly = new CircleOptions()
                                .center(marker.getPosition())
                                .radius(sights.get(i).getRadius())
                                .strokeColor(Color.parseColor("#107DAC"))
                                .fillColor(Color.parseColor("#2271cce7"));
                        radiusContainer.add(circly);
                        googleMap.addMarker(marker);
                        googleMap.addCircle(circly);

                    }
                });

                moveZoomControls(mapView, -20, -20, 950, 1350, true, true);

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);

                //virker ikke
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null);

    }


    @SuppressLint("MissingPermission")
    private void checkGPS() {

        //get current location from the fused client
        //update UI
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


    }

    private void checkpermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        viewModel = new MapViewModel();
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

    private void checkLocationToMarker(Location location) {

        this.currentLocation = location;
        for (int i = 0; i < markerOptions.size() ; i++) {

                float[] distance = new float[1];
                Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        markerOptions.get(i).getPosition().latitude, markerOptions.get(i).getPosition().longitude, distance);

                if (distance[0] > radiusContainer.get(i).getRadius()) {
                    //outside

                } else {
                    //inside
                    Log.d("dsa", markerOptions.get(i).getTitle());
                    onMapsEnterListener.EnteredZone(ids.get(i).toString());
                }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        callback = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onMapsEnterListener = (OnMapsEnterListener) context;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    //zoomControl bar relocated
    MapView mMapView;
    private static final String GOOGLEMAP_ZOOMIN_BUTTON = "GoogleMapZoomInButton";
    private View mapView = null;

    private void moveZoomControls(View mapView, int left, int top, int right, int bottom, boolean horizontal, boolean vertical) {

        assert mapView != null;

        View zoomIn = mapView.findViewWithTag(GOOGLEMAP_ZOOMIN_BUTTON);

        // we need the parent view of the zoomin/zoomout buttons - it didn't have a tag
        // so we must get the parent reference of one of the zoom buttons
        View zoomInOut = (View) zoomIn.getParent();

        if (zoomInOut != null) {
            moveView(zoomInOut, left, top, right, bottom, horizontal, vertical);
        }
    }

    private void moveView(View view, int left, int top, int right, int bottom, boolean horizontal, boolean vertical) {
        try {
            assert view != null;

            // replace existing layout params
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (left >= 0) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            }

            if (top >= 0) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }

            if (right >= 0) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            }

            if (bottom >= 0) {
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            }

            if (horizontal) {
                rlp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            }

            if (vertical) {
                rlp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            }

            rlp.setMargins(left, top, right, bottom);

            view.setLayoutParams(rlp);
        } catch (Exception ex) {
            Log.e("sometinh", "moveView() - failed: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }



    //finger pushPull






}
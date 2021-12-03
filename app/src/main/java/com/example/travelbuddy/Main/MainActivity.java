package com.example.travelbuddy.Main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;

// import com.example.travelbuddy.databinding.ActivityMainBinding;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{


    Button scanbutton;
    BottomNavigationView  navview;
    SharedViewModel sharedViewModel;
   // private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navview = findViewById(R.id.nav_view);
        navview.bringToFront();
        scanbutton = findViewById(R.id.scanbtn);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        scanbutton.setOnClickListener(view -> {
            Intent intent = new Intent (MainActivity.this,QRscanactivity.class);
            startActivity(intent);
        });

        navview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.MapFragment){
                    Fragmenthandler("MapFragment");
                }
                return true;
            }
        });

      /*  binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       */

       /* BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment_container_view);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        scanbutton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,QRscanactivity.class);
            startActivity(intent);
        });
        */

    }


    void checkifscannedqr(){
        if(sharedViewModel.getQrscanned()){
            scanbutton.setVisibility(View.INVISIBLE);
        } else {
            // do something else
        }
    }

    void Fragmenthandler(String fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        String classpath = "com.example.travelbuddy.View." + fragment;
        try {
            Class<?> cls = Class.forName(classpath);
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, (Class<? extends Fragment>) cls,null).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
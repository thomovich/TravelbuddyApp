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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.travelbuddy.R;
import com.example.travelbuddy.View.AboutFragment;
import com.example.travelbuddy.View.HomeFragment;
import com.example.travelbuddy.View.MapFragment;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

// import com.example.travelbuddy.databinding.ActivityMainBinding;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{


    Button scanbutton;
    BottomNavigationView  navview;
    SharedViewModel sharedViewModel;
   // private ActivityMainBinding binding;
   ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //onBoarding features for the app
        //Intent intent = new Intent (MainActivity.this,OnboardingActivity.class);
        //startActivity(intent);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        bottomMenu();

    }
    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                Log.d("State",""+i);
                Log.d("State",""+R.id.home);
                Log.d("State",""+R.id.map);
                Log.d("State",""+R.id.about);
                switch (i){

                    case R.id.HomeFragment:

                        fragment = new HomeFragment();
                        break;

                    case R.id.MapFragment:

                        fragment = new MapFragment();
                        break;

                    case R.id.AboutFragment:

                        fragment = new AboutFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }


        /*scanbutton = findViewById(R.id.scanbtn);


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
        });*/

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
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, (Class<? extends Fragment>) cls,null).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
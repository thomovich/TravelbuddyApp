package com.example.travelbuddy.Main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaDataSource;
import android.media.MediaPlayer;

import android.os.Bundle;


import android.os.Handler;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;

import android.widget.Button;
import android.widget.SeekBar;


import com.example.travelbuddy.Models.Senddata;
import com.example.travelbuddy.R;

import com.example.travelbuddy.ViewModels.MainActivityViewModel;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    Button scanbutton, playbtn;
    SharedViewModel sharedViewModel;
    MainActivityViewModel mainActivityViewModel;
    MediaPlayer mediaPlayer;
    boolean isprepared = false;
    SeekBar seekbar;
    int seekbarmax;
   // private ActivityMainBinding binding;
   ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //onBoarding features for the app
        //Intent intent = new Intent(MainActivity.this,OnboardingActivity.class);
        //startActivity(intent);
        setContentView(R.layout.activity_main);
        playbtn = findViewById(R.id.btnplay);
        seekbar = findViewById(R.id.playbar);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getsong().observe(this, m ->{
            mediaPlayer = m;
            seekbar.setMax(mediaPlayer.getDuration());
        } );
        mainActivityViewModel.getButtontext().observe(this, s -> playbtn.setText(s));
        mainActivityViewModel.getSeekbar().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                seekbar.setProgress(integer);
            }
        });

        if(savedInstanceState == null){
            try {
                fetchaudiofromRepo("intro");
                Fragmenthandler("HomeFragment");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        playbtn.setOnClickListener(v->{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                mainActivityViewModel.selectbtntext("sound is paused");
            } else {
                mediaPlayer.start();
                updateSeekbar();
                mainActivityViewModel.selectbtntext("sound is playing");

            }
        });
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        //Fragmenthandler("HomeFragment");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromuser) {
                if(fromuser){
                    mediaPlayer.seekTo(i);
                    seekBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bottomMenu();

    }

    private void updateSeekbar() {
        int currpos = mediaPlayer.getCurrentPosition()/100;
        mainActivityViewModel.selectseekbar(currpos*100);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 10);
    }


    private void fetchaudiofromRepo(String song) throws FileNotFoundException {
        mainActivityViewModel.selectbtntext("Loading sound");
        mainActivityViewModel.selectSong("test");
        mainActivityViewModel.selectbtntext("Sound ready");
    }


    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(i -> {

            try {
                switch (i) {
                    case R.id.HomeFragment:
                        Fragmenthandler("HomeFragment");
                        break;
                    case R.id.MapFragment:
                        Fragmenthandler("MapFragment");
                        break;
                    case R.id.AboutFragment:
                        Fragmenthandler("AboutFragment");
                        break;
                    case R.id.SightFragment:
                        Fragmenthandler("SightFragment");
                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
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
        String classpath = "com.example.travelbuddy.View." + fragment;
        try {
            Class<?> cls = Class.forName(classpath);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, (Class<? extends Fragment>) cls,null).addToBackStack(null).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
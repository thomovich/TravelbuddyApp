package com.example.travelbuddy.Main;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.Bundle;


import android.view.View;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.travelbuddy.R;

import com.example.travelbuddy.View.HomeFragment;

import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.IOException;


public class MainActivity extends AppCompatActivity{
    private boolean playing = false;
    Button scanbutton, playbtn;
    BottomNavigationView  navview;
    SharedViewModel sharedViewModel;
    MediaPlayer mediaPlayer;
    RelativeLayout relativeLayout;
   // private ActivityMainBinding binding;
   ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //onBoarding features for the app
        //Intent intent = new Intent (MainActivity.this,OnboardingActivity.class);
        //startActivity(intent);
        setContentView(R.layout.activity_main);


        playbtn = findViewById(R.id.btnplay);
        playbtn.setOnClickListener(v->{
            playbtn.setText("sound is playing");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    playaudio();
                }
            }).start();

        });
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        Fragmenthandler("HomeFragment");

        bottomMenu();

    }

    private void playaudio() {
        String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        //Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }


    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {


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
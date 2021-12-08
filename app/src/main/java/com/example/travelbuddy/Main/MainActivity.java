package com.example.travelbuddy.Main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;


import com.example.travelbuddy.Models.Senddata;
import com.example.travelbuddy.Models.Soundplayer;
import com.example.travelbuddy.R;

import com.example.travelbuddy.View.HomeFragment;

import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, Senddata {
    String currentlyloaded;
    Button scanbutton, playbtn;
    SharedViewModel sharedViewModel;
    MediaPlayer mediaPlayer;
    SeekBar seekbar;
   // private ActivityMainBinding binding;
   ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //onBoarding features for the app
        //Intent intent = new Intent (MainActivity.this,OnboardingActivity.class);
        //startActivity(intent);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        playbtn = findViewById(R.id.btnplay);
        seekbar = findViewById(R.id.playbar);

        playbtn.setClickable(false);
        playbtn.setText("Audio is loading");
        fetchnewsound("gs://travelbuddy-2b732.appspot.com/Ta' og fuck af Video + Lyrics.mp3");



        playbtn.setOnClickListener(v->{

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                playbtn.setText("sound is paused");
            } else {
                playbtn.setText("sound is playing");
                mediaPlayer.start();
            }



        });
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        Fragmenthandler("HomeFragment");

        bottomMenu();

    }



    private void fetchAudioUrlFromFirebase(String url) {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(url);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);
                    // wait for media player to get prepare
                    mediaPlayer.setOnPreparedListener(MainActivity.this);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", e.getMessage());
                    }
                });

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playbtn.setText("audio is ready");
        playbtn.setClickable(true);
        mediaPlayer = mp;
    }

    private void fetchnewsound(String data){
        if(currentlyloaded != data){
            currentlyloaded = data;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    playbtn.setClickable(false);
                    playbtn.setText("Audio is loading");
                    fetchAudioUrlFromFirebase(data);
                }
            }).start();
        } else {
            // no new sounds to get
        }
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


    @Override
    public void senddata(String data) {
        fetchnewsound(data);

    }
}
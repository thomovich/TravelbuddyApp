package com.example.travelbuddy.Main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
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

import com.example.travelbuddy.ViewModels.MainActivityViewModel;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, Senddata {
    String currentlyloaded, btntext;
    Button scanbutton, playbtn;
    SharedViewModel sharedViewModel;
    MainActivityViewModel mainActivityViewModel;
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

        playbtn = findViewById(R.id.btnplay);
        seekbar = findViewById(R.id.playbar);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        btntext = mainActivityViewModel.getBtntext();
        currentlyloaded = mainActivityViewModel.getCrnsong();
        playbtn.setText(btntext);
        mediaPlayer = mainActivityViewModel.getSong();


        mainActivityViewModel.getButtontext().observe(this, s -> playbtn.setText(s));

        mainActivityViewModel.getCurrentsong().observe(this, s -> currentlyloaded = s);
        mainActivityViewModel.getSeekbar().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                seekbar.setProgress(integer);
            }
        });

        fetchnewsound("gs://travelbuddy-2b732.appspot.com/Ta' og fuck af Video + Lyrics.mp3");
        if(mediaPlayer != null){
            seekbar.setMax(mediaPlayer.getDuration());
        }



        playbtn.setOnClickListener(v->{

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                mainActivityViewModel.selectbtntext("sound is paused");
            } else {
                mainActivityViewModel.selectbtntext("sound is playing");
                mediaPlayer.start();
            }

        });
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);

        Fragmenthandler("HomeFragment");

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



    private void fetchAudioUrlFromFirebase(String url) {
        mediaPlayer = new MediaPlayer();
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
        mainActivityViewModel.selectbtntext("Song is ready");
        mainActivityViewModel.setSong(mp);
        playbtn.setClickable(true);
        updateSeekbar();
        seekbar.setMax(mediaPlayer.getDuration());
        mediaPlayer = mp;
    }

    private void updateSeekbar() {
        int currpos = mediaPlayer.getCurrentPosition();
        mainActivityViewModel.selectseekbar(currpos);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    private void fetchnewsound(String data){
        if(currentlyloaded != data){
            mainActivityViewModel.selectcurrentsong(data);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    playbtn.setClickable(false);
                    mainActivityViewModel.selectbtntext("song is loading");
                    fetchAudioUrlFromFirebase(data);
                }
            }).start();
        } else {
            // no new sounds to get
        }
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
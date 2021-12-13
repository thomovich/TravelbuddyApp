package com.example.travelbuddy.Main;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.travelbuddy.Listener.OnMapsEnterListener;
import com.example.travelbuddy.Models.GlobalVariable;
import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.MainActivityViewModel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import java.io.FileNotFoundException;


public class MainActivity extends AppCompatActivity implements OnMapsEnterListener {
    Button playbtn;
    MainActivityViewModel mainActivityViewModel;
    MediaPlayer mediaPlayer;
    SeekBar seekbar;
   ChipNavigationBar chipNavigationBar;
   Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!GlobalVariable.getInstance().isscan){
            Intent intent = new Intent(MainActivity.this,QRscanactivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        playbtn = findViewById(R.id.btnplay);
        seekbar = findViewById(R.id.playbar);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getsong().observe(this, m ->{
            mediaPlayer = m;
            seekbar.setMax(mediaPlayer.getDuration());
            playbtn.setClickable(true);
            if(runnable == null){
                updateSeekbar();
            }
        } );
        mainActivityViewModel.getButtontext().observe(this, s -> playbtn.setText(s));
        mainActivityViewModel.getSeekbar().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                seekbar.setProgress(integer);
            }
        });

        if(savedInstanceState == null) {
            Fragmenthandler("HomeFragment");
        }

        playbtn.setOnClickListener(v->{
            //Avoid nullpointers with simple check
            if(mediaPlayer == null){
                return;
            }
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                mainActivityViewModel.selectbtntext("sound is paused");
            } else {
                mediaPlayer.start();
                mainActivityViewModel.selectbtntext("sound is playing");

            }
        });
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);


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
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 100);
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


    void Fragmenthandler(String fragment){
        String classpath = "com.example.travelbuddy.View." + fragment;
        try {
            Class<?> cls = Class.forName(classpath);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, (Class<? extends Fragment>) cls,null).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void EnteredZone(String sound) {
        if(sound != mainActivityViewModel.currentlyloaded){
            mainActivityViewModel.currentlyloaded = sound;
            try {
                fetchaudiofromRepo(sound);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Sound already loaded", "TAG");
        }

    }
}
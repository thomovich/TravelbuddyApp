package com.example.travelbuddy.Main;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.MediaDataSource;
import android.media.MediaPlayer;

import android.os.Bundle;


import android.os.Handler;
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


public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, Senddata {
    String currentlyloaded;
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
        getData();
        mainActivityViewModel.getButtontext().observe(this, s -> playbtn.setText(s));
        mainActivityViewModel.getCurrentsong().observe(this, s -> currentlyloaded = s);
        mainActivityViewModel.getSeekbar().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                seekbar.setProgress(integer);
            }
        });
        try {
            fetchaudiofrombase64("tag og fuck af");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //fetchnewsound("gs://travelbuddy-2b732.appspot.com/Ta' og fuck af Video + Lyrics.mp3");
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

    private void getData() {
        currentlyloaded = mainActivityViewModel.getCrnsong();
        playbtn.setText(mainActivityViewModel.getBtntext());
        mediaPlayer = mainActivityViewModel.getSong();
        if(mediaPlayer != null){
            seekbar.setMax(mediaPlayer.getDuration());
        }
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
                    //fetchAudioUrlFromFirebase(data);
                }
            }).start();
        } else {
            // no new sounds to get
        }
    }

    private void fetchaudiofrombase64(String song) throws FileNotFoundException {
        if(song.equals(mainActivityViewModel.getCrnsong())){
            return;
        }
        mainActivityViewModel.selectcurrentsong(song);
       String base64 = "";
       StringBuffer sbuffer = new StringBuffer();
       InputStream ins = this.getResources().openRawResource(R.raw.base64);
       BufferedReader reader = new BufferedReader(new InputStreamReader(ins));{
           if(ins != null){
               try {
                   while((base64 = reader.readLine()) != null){
                       sbuffer.append(base64);
                   }
                   base64 = sbuffer.toString();
                   ins.close();
               } catch (Exception e){
                   e.printStackTrace();
               }
           }
       };
        mediaPlayer = new MediaPlayer();
        mainActivityViewModel.selectbtntext("Song is loading");
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        mediaPlayer.setDataSource(new MediaDataSource() {
            @Override
            public long getSize() throws IOException {
                return data.length;
            }

            @Override
            public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
                int length = (int)getSize();
                if (position >= length) return -1; // EOF
                if (position + size > length) // requested more than available
                    size = length - (int)position; // set size to maximum size possible
                // at given position

                System.arraycopy(data, (int) position, buffer, offset, size);
                return size;
            }



            @Override
            public void close() throws IOException {

            }
        });
        mediaPlayer.setOnPreparedListener(MainActivity.this);
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    @Override
    public void senddata(String data) {
        fetchnewsound(data);

    }


}
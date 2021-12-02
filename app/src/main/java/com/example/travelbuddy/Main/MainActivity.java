package com.example.travelbuddy.Main;

import static androidx.constraintlayout.motion.widget.MotionLayout.MyTracker.me;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;


import com.example.travelbuddy.R;
import com.google.zxing.Result;



import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{

    Button scanbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanbutton = findViewById(R.id.scanbtn);

        scanbutton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,QRActivity.class);
            startActivity(intent);
        });


    }


}
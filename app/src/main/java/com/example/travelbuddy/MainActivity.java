package com.example.travelbuddy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;


import com.google.zxing.Result;



import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;
    private static int cam = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Button scanbutton;
    int currentapiversion = Build.VERSION.SDK_INT;
    private String qrCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        if(currentapiversion>= Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(this,"Permission granted", Toast.LENGTH_SHORT);
            } else {
                requestpermission();
            }
        }
        scannerView = findViewById(R.id.scannerview);
        scanbutton = findViewById(R.id.scanbtn);
        scanbutton.setOnClickListener(view -> {

        });


    }

    private boolean checkPermission(){
        return(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestpermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionResult(int requestcode, String permission[], int[] grantResult){
        switch(requestcode){
            case REQUEST_CAMERA:
                if(grantResult.length > 0){
                    boolean cameraAccept = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccept){
                        Toast.makeText(this, "Tilladelse givet", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "Giv tilladelse for at bruge app", Toast.LENGTH_SHORT).show();
                        requestpermission();
                    }
                }
                break;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        int currentapiversion = Build.VERSION.SDK_INT;
        if(currentapiversion >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
        scannerView = null;
    }

    @Override
    public void handleResult(Result result) {
        final String rawresult = result.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");

        builder.setPositiveButton(
                "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scannerView.resumeCameraPreview(MainActivity.this);
                    }
                }
        );

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onDestroy();
            }
        });

        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
package com.example.travelbuddy.Main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.*;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelbuddy.Models.GlobalVariable;
import com.example.travelbuddy.R;
import com.example.travelbuddy.Reposity.GetDataFromDb;
import com.example.travelbuddy.Reposity.dblookups;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanactivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;
    int currentapiversion = Build.VERSION.SDK_INT;
    private String qrCode;
    Spinner spinner;
    CameraManager cameraManager;



    ImageButton switchOff,switchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qractivity);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        switchOff = findViewById(R.id.switch_off);
        switchOn = findViewById(R.id.switch_on);

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                switchOn.setVisibility(View.VISIBLE);
            } else {
                //Device has no flash
            }
        } else {
            //Device has no camera
        }

        switchOff.setOnClickListener(v -> {
            switchOff.setVisibility(View.INVISIBLE);
            switchOn.setVisibility(View.VISIBLE);
            //flashcontrol(false);

        });
        switchOn.setOnClickListener(view -> {
            switchOff.setVisibility(View.VISIBLE);
            switchOn.setVisibility(View.INVISIBLE);
            //flashcontrol(true);


        });

        scannerView = findViewById(R.id.zxscanner);
        Button manualbtn = findViewById(R.id.typeinbtn);

        if(currentapiversion>= Build.VERSION_CODES.M){
            if(checkPermission()){
            } else {
                requestpermission();
            }
        }

        manualbtn.setOnClickListener(v->{
            final Dialog dialog = new Dialog(QRscanactivity.this);
            if(dialog.isShowing()){
                return;
            }
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setCanceledOnTouchOutside(false);
            TextView textView;
            Button buttonok, buttoncancel;
            textView = dialog.findViewById(R.id.txt_dia);
            buttonok = dialog.findViewById(R.id.btn_ok);
            buttoncancel = dialog.findViewById(R.id.btn_cancel);

            buttonok.setOnClickListener(view -> {
                EditText edit = dialog.findViewById(R.id.typeinqr);
                String qrcode = edit.getText().toString();
                if(Checkindb(qrcode)){
                    Createspinner(qrcode);
                    dialog.dismiss();
                } else {
                    textView.setText("Wrong qr code");
                }


            });

            buttoncancel.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });




    }

    private boolean Checkindb(String qrcode) {
        dblookups dbl = GetDataFromDb.getSingleinstance();
        return dbl.checkqr(qrcode);
    }

    private boolean checkPermission(){
        return(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestpermission(){
        ActivityCompat.requestPermissions(QRscanactivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionResult(int requestcode, String[] permission, int[] grantResult){
        switch(requestcode){
            case REQUEST_CAMERA:
                if(grantResult.length > 0){
                    boolean cameraAccept = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccept){
                        Toast.makeText(this, "Tilladelse givet", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "Giv tilladelse til kamera for at bruge app", Toast.LENGTH_SHORT).show();
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
                    scannerView = findViewById(R.id.zxscanner);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else
                requestpermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraManager = null;
        scannerView.stopCamera();
        scannerView = null;

    }

    void goNextActivity(int qrcode, boolean scanned){
        GlobalVariable.getInstance().qrcode = qrcode;
        GlobalVariable.getInstance().isscan = scanned;
        Intent intent = new Intent(QRscanactivity.this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleResult(Result result) {
        final String rawresult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");
        boolean check = Checkindb(rawresult);
        builder.setPositiveButton(
                "ok", (dialogInterface, i) -> {
                    if(check){
                        int rawint = Integer.parseInt(rawresult);
                        builder.setTitle("QR succesful press ok");
                        Createspinner(rawresult);
                    } else {
                        builder.setTitle("scan unsuccesful try again");
                    }
                    scannerView.resumeCameraPreview(QRscanactivity.this);
                }
        );
        builder.setNegativeButton(
                "Cancel", (dialogInterface, i) -> {
                    scannerView.resumeCameraPreview(QRscanactivity.this);
                });
        if(check){
            builder.setMessage("QR scanned succesfully");
        } else {
            builder.setMessage("QR not correct");
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if(getApplicationContext() == null){
            alertDialog.dismiss();
        }
    }

    void flashcontrol(boolean flash){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode("0", flash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Createspinner(String qrcode){
        int qrcodeint = Integer.parseInt(qrcode);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.language_dialog);
        dialog.setCanceledOnTouchOutside(false);
        Button buttonok;

        buttonok = dialog.findViewById(R.id.languageok);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String selected = spinner.getSelectedItem().toString();
                GlobalVariable.getInstance().languagechosen = selected;
                Log.d(selected, "selected lang");
                goNextActivity(qrcodeint,true);
            }
        });
        spinner = new Spinner(this);
        spinner = dialog.findViewById(R.id.languagedropdown);
        GetDataFromDb getDataFromDb = GetDataFromDb.getSingleinstance();
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray = getDataFromDb.getLanguages(qrcodeint);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.my_spinner_style,
                        spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.my_spinner_style);
        spinner.setAdapter(spinnerArrayAdapter);
        dialog.show();
    }

}
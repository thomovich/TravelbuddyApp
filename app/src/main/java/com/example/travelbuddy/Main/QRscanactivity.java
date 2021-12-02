package com.example.travelbuddy.Main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.travelbuddy.R;
import com.example.travelbuddy.Reposity.GetDataFromDb;
import com.example.travelbuddy.Reposity.dblookups;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanactivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;
    private static final int cam = Camera.CameraInfo.CAMERA_FACING_BACK;
    int currentapiversion = Build.VERSION.SDK_INT;
    private String qrCode;
    private SharedViewModel shared;

    ImageButton switchOff,switchOn;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qractivity);

        switchOff = findViewById(R.id.switch_off);
        switchOn = findViewById(R.id.switch_on);

        camera = Camera.open();
        Camera.Parameters parameters = Camera.open().getParameters();

        switchOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switchOff.setVisibility(View.GONE);
                switchOn.setVisibility(View.VISIBLE);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters((parameters));
                camera.startPreview();
            }
        });
        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchOff.setVisibility(View.VISIBLE);
                switchOn.setVisibility(View.GONE);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters((parameters));
                camera.stopPreview();
            }
        });



        scannerView = findViewById(R.id.zxscanner);
        Button manualbtn = findViewById(R.id.typeinbtn);
        shared = new ViewModelProvider(this).get(SharedViewModel.class);

        if(currentapiversion>= Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(this,"Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                requestpermission();
            }
        }

        manualbtn.setOnClickListener(v->{
            final Dialog dialog = new Dialog(QRscanactivity.this);
            dialog.setContentView(R.layout.custom_dialog);

            Button buttonok, buttoncancel;
            buttonok = dialog.findViewById(R.id.btn_ok);
            buttoncancel = dialog.findViewById(R.id.btn_cancel);

            buttonok.setOnClickListener(view -> {
                EditText edit = dialog.findViewById(R.id.typeinqr);
                String qrcode = edit.getText().toString();
                shared.setQrscanned(Checkindb(qrcode));
                dialog.dismiss();
            });

            buttoncancel.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });




    }

    private boolean Checkindb(String qrcode) {
        dblookups dbl = new GetDataFromDb();
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
        scannerView.stopCamera();
        scannerView = null;
    }

    @Override
    public void handleResult(Result result) {
        final String rawresult = result.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");

        builder.setPositiveButton(
                "ok", (dialogInterface, i) -> {
                    shared.setQrscanned(Checkindb(rawresult));
                    scannerView.resumeCameraPreview(QRscanactivity.this);
                }
        );

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> onDestroy());

        builder.setMessage(result.getText());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
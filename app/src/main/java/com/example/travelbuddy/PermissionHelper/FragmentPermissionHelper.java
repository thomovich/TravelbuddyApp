package com.example.travelbuddy.PermissionHelper;

import android.Manifest;
import android.app.Instrumentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

public class FragmentPermissionHelper {

    public void startpermisionrequest(FragmentActivity fragmentActivity){
        ActivityResultLauncher<String> resultLauncher = fragmentActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted ->{
            if(isGranted){

            }
            else {

            }
        });
        resultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
}

package com.example.travelbuddy.Main;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.MainActivityViewModel;
import com.example.travelbuddy.ViewModels.SharedViewModel;

public class MainActivity extends AppCompatActivity{

    Button scanbutton;
    private MainActivityViewModel viewModel;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        chekifqrscanned();
        setContentView(R.layout.activity_main);
        scanbutton = findViewById(R.id.scanbtn);

        scanbutton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,QRscanactivity.class);
            startActivity(intent);
        });


    }

    private void chekifqrscanned() {
        if(sharedViewModel.getQrscanned()){
            scanbutton.setVisibility(View.INVISIBLE);
        } else {

        }
    }

    @Override
    protected void onResume() {
        chekifqrscanned();
        super.onResume();
    }
}
package com.example.travelbuddy.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.SharedViewModel;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*button = findViewById(R.id.buttonToMain);

        button.setOnClickListener(view -> {
            Intent intent = new Intent (OnboardingActivity.this,MainActivity.class);
            startActivity(intent);
        });*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.onboarding_fragment_container,paperOnboardingFragment);
        fragmentTransaction.commit();
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {

        PaperOnboardingPage src = new PaperOnboardingPage("Search","Now that you have scanned your QR code, a set of sights have been unlocked so that you now can find the ones you find interesting",
                Color.parseColor("#dce9f1"),R.mipmap.ic_searchicon_foreground,R.drawable.ic_search_standard_foreground);

        PaperOnboardingPage src1 = new PaperOnboardingPage("Navigate","With the items in place, you can now explore the places! so what are you waiting for?",
                Color.parseColor("#dce9f1"),R.mipmap.ic_navigate_foreground,R.drawable.ic_navigate_standard_foreground);

        PaperOnboardingPage src2 = new PaperOnboardingPage("Listen","Now that you have found a item, you can sit back, relax and listen to the sight",
                Color.parseColor("#dce9f1"),R.mipmap.ic_listen_foreground,R.drawable.ic_listen_standard_foreground);
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(src);
        elements.add(src1);
        elements.add(src2);
        return elements;
    }
}
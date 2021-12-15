package com.example.travelbuddy.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.travelbuddy.R;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.onboarding_fragment_container,paperOnboardingFragment);
        fragmentTransaction.commit();
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {

        PaperOnboardingPage src = new PaperOnboardingPage(getResources().getString(R.string.search),getResources().getString(R.string.unlock),
                Color.parseColor("#804DA6F9"),R.mipmap.onboardimg1_foreground,R.drawable.ic_search_standard_foreground);

        PaperOnboardingPage src1 = new PaperOnboardingPage(getResources().getString(R.string.navigate),getResources().getString(R.string.travel),
                Color.parseColor("#804DF961"),R.mipmap.onboardimg2_foreground,R.drawable.ic_navigate_standard_foreground);

        PaperOnboardingPage src2 = new PaperOnboardingPage(getResources().getString(R.string.lyt),getResources().getString(R.string.story),
                Color.parseColor("#80FFFFFF"),R.mipmap.onboardimg3_foreground,R.drawable.ic_listen_standard_foreground);
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(src);
        elements.add(src1);
        elements.add(src2);
        return elements;
    }


    public void navigateToMain(View view) {
        Intent intent = new Intent(OnboardingActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
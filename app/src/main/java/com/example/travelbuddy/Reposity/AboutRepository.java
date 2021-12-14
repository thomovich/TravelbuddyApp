package com.example.travelbuddy.Reposity;


import com.example.travelbuddy.R;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.travelbuddy.Models.About;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AboutRepository {

    private ArrayList<About> aboutList = new ArrayList<>();
    private final MutableLiveData<List<About>> Abouts = new MutableLiveData<>();

    public AboutRepository() {
    if (Locale.getDefault().getLanguage().equals("da")) {
        aboutList.add(new About("Dette er Asbjørn. Han er 24 år gammel og er vores Lead-Designer", R.drawable.asbjorn));
        aboutList.add(new About("Dette er Frederik. Han er 26 år gammel og arbejder som CEO", R.drawable.frederik));
        aboutList.add(new About("Dette er Simon. Han er 22 år gammel or arbejder som CEO", R.drawable.simon));
        aboutList.add(new About("Dette er rasmus. Han er 25 år gammel og arbejder som vicevært", R.drawable.rasmus));
        aboutList.add(new About("Dette er Thomas. Han er 25 år gammel og arbejder som HR-medarbejder", R.drawable.thomas));
        aboutList.add(new About("Virksomheden er 6 måneder gammel. tryk for at læse mere",R.drawable.travelbuddyfinal));

        Abouts.setValue(aboutList);
    }
    else if (Locale.getDefault().getLanguage().equals("en")) {
        aboutList.add(new About("This is Asbjørn. He is 24 years old and is our lead designer", R.drawable.asbjorn));
        aboutList.add(new About("This is Frederik. He is 26 years old and work in the company as the CE", R.drawable.frederik));
        aboutList.add(new About("This is Simon. He is 22 years old, and work as CEO", R.drawable.simon));
        aboutList.add(new About("This is rasmus. He is 25 years old. He is in charge of all pickles.", R.drawable.rasmus));
        aboutList.add(new About("This is Thomas. He is head og the janitors in the company", R.drawable.thomas));
        aboutList.add(new About("The company is 6 months old. Click to read more",R.drawable.travelbuddyfinal));

        Abouts.setValue(aboutList);
    }
    }

    public LiveData<List<About>> getAllAbouts() {

        return Abouts;

    }
}


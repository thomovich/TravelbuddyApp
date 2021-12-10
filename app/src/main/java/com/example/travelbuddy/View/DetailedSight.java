package com.example.travelbuddy.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.SightViewModel;

public class DetailedSight extends Fragment {

    TextView detailedtext;
    ImageView Img;
    Button backbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailedsight, container, false);

        detailedtext = view.findViewById(R.id.detailedtext);
        Img = view.findViewById(R.id.detailedphoto);
        backbtn = view.findViewById(R.id.backbutton);
        backbtn.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        });
    return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SightViewModel viewModel = new ViewModelProvider(requireActivity()).get(SightViewModel.class);

        viewModel.getSights().observe(getViewLifecycleOwner(), Sights->{
            this.detailedtext.setText("test");
            this.detailedtext.setText(Sights.getDetailedinfo());
            this.Img.setImageResource(Sights.getImg());
        }  );
    }
}

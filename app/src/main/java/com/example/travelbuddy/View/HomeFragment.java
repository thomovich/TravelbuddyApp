package com.example.travelbuddy.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.travelbuddy.R;

public class HomeFragment extends Fragment {
    private Button sdgbutton;
    private ImageView img1, img2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        img1 = rootView.findViewById(R.id.imageView4);
        img2 = rootView.findViewById(R.id.imageView5);
        sdgbutton = rootView.findViewById(R.id.sdgbutton);

        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sdgbutton.setOnClickListener(v->{
                img2.setVisibility(View.VISIBLE);
                img1.setVisibility(View.VISIBLE);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
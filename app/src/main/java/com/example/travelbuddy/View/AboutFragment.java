package com.example.travelbuddy.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelbuddy.Adapters.AboutAdapter;
import com.example.travelbuddy.Adapters.SightAdapter;
import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.AboutViewModel;
import com.example.travelbuddy.ViewModels.SightViewModel;


public class AboutFragment extends Fragment implements AboutAdapter.OnListItemClickListener {

    AboutViewModel viewModel;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        recyclerView = view.findViewById(R.id.rv_about);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        AboutAdapter adapter = new AboutAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        viewModel.getAllAbouts().observe(getViewLifecycleOwner(), adapter::updateList);

        return view;

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
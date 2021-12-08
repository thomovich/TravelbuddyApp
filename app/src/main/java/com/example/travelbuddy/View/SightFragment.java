package com.example.travelbuddy.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbuddy.Adapters.SightAdapter;
import com.example.travelbuddy.R;
import com.example.travelbuddy.ViewModels.SightViewModel;

public class SightFragment extends Fragment implements SightAdapter.OnListItemClickListener {

    SightViewModel viewModel;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sight, container, false);
        recyclerView = view.findViewById(R.id.rv_sight);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        SightAdapter adapter = new SightAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SightViewModel.class);
        viewModel.getAllSights().observe(getViewLifecycleOwner(), adapter::updateList);

        return view;

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}

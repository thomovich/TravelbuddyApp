package com.example.travelbuddy.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        int sightNumber = clickedItemIndex + 1;
        switch (sightNumber) {
            case 1:
                Toast.makeText(getActivity(), "Møllestien was created in 1915 and showcases some of the most beautiful houses in Århus.: " + sightNumber, Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "Marselisborg Slot is the place the royal family spend some of their holidays and vacations: " + sightNumber, Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "Beautiful spring in the center of Århus! Student often spend time bathing in its waters: " + sightNumber, Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "Created as a gift for Århus, this statue, is located near the trainstation in a little park: " + sightNumber, Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(getActivity(), "This statue is great for kids, since the stones surrounding it are met for climbing: " + sightNumber, Toast.LENGTH_LONG).show();
                break;
        }
    }
}

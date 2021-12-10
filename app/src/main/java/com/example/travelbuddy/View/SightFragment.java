package com.example.travelbuddy.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    int lastpos;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sight, container, false);
        recyclerView = view.findViewById(R.id.rv_sight);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        lastpos = preferences.getInt("LastPosSight",0);
        recyclerView.scrollToPosition(lastpos);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastpos = mLayoutManager.findFirstVisibleItemPosition();
            }
        });
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onListItemClick(int clickedItemIndex) {
        viewModel.selectSights(viewModel.getAllSights().getValue().get(clickedItemIndex));
        requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, DetailedSight.class , null).addToBackStack(null).commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SightAdapter adapter = new SightAdapter(this);
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(SightViewModel.class);
        viewModel.getAllSights().observe(getViewLifecycleOwner(), adapter::updateList);

    }




    @Override
    public void onPause() {
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("LastPosSight", lastpos);
        e.apply();
        super.onPause();
    }


}

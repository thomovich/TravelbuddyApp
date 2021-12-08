package com.example.travelbuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbuddy.Models.Sights;
import com.example.travelbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class SightAdapter extends RecyclerView.Adapter<SightAdapter.ViewHolder> {
    // We want the data from the Craving objects to the RecycleView items.
    //Set to new ArrayList to make sure it is not NULL, before we get first Live data update.
    //Create new items, Populates the items with data, and return the information.
    private List<Sights> sightList = new ArrayList<>();
    final private OnListItemClickListener mOnListItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView SightDescription;
        ImageView icon;

        // ViewHolder inner class contains the Views that will be send to the RecyclerView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SightDescription = itemView.findViewById(R.id.SightDescription);
            icon = itemView.findViewById(R.id.sight_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getBindingAdapterPosition());
        }
    }

    public SightAdapter(OnListItemClickListener listener) {

        mOnListItemClickListener = listener;
    }

    @NonNull
    @Override
    //This is the layout we are gonna use for the single item in our RecycleView
    public SightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sight_item, parent, false);
        return new SightAdapter.ViewHolder(view);
    }


    //Get the data from the single craving java object into the views of our cravingholder.
    public void onBindViewHolder(@NonNull SightAdapter.ViewHolder holder, int position) {
        holder.SightDescription.setText(sightList.get(position).getName());
        holder.icon.setImageResource(sightList.get(position).getId());
    }

    public void updateList(List<Sights> sightList) {
        this.sightList = sightList;
        notifyDataSetChanged();
    }

    public int getItemCount() {

        return sightList.size();
    }


    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
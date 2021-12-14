package com.example.travelbuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbuddy.Models.About;
import com.example.travelbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    // We want the data from the About objects to the RecycleView items.
    //Set to new ArrayList to make sure it is not NULL, before we get first Live data update.
    //Create new items, Populates the items with data, and return the information.
    private List<About> aboutList = new ArrayList<>();
    final private OnListItemClickListener mOnListItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView AboutDescription;
        ImageView icon;

        // ViewHolder inner class contains the Views that will be send to the RecyclerView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AboutDescription = itemView.findViewById(R.id.AboutDescription);
            icon = itemView.findViewById(R.id.about_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getBindingAdapterPosition());
        }
    }

    public AboutAdapter(OnListItemClickListener listener) {

        mOnListItemClickListener = listener;
    }

    @NonNull
    @Override
    //This is the layout we are gonna use for the single item in our RecycleView
    public AboutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_item, parent, false);
        return new AboutAdapter.ViewHolder(view);
    }

    //Get the data from the single about java object into the views of our aboutolder.
    public void onBindViewHolder(@NonNull AboutAdapter.ViewHolder holder, int position) {
        holder.AboutDescription.setText(aboutList.get(position).getName());
        holder.icon.setImageResource(aboutList.get(position).getId());
    }

    public void updateList(List<About> cravingList) {
        this.aboutList = cravingList;
        notifyDataSetChanged();
    }

    public int getItemCount() {

        return aboutList.size();
    }


    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}


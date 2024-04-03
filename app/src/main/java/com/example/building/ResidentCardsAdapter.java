package com.example.building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResidentCardsAdapter extends RecyclerView.Adapter<ResidentCardsAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<ArrayList<String>> courseModelArrayList;

    // Constructor
    public ResidentCardsAdapter(Context context, ArrayList<ArrayList<String>> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public ResidentCardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_resident_card, parent, false);
        return new ResidentCardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidentCardsAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        ArrayList<String> model = courseModelArrayList.get(position);
        holder.nameTV.setText(model.get(0));
        holder.mobileTV.setText(model.get(1));
        holder.blockTV.setText(model.get(2));
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTV;
        private final TextView mobileTV;
        private final TextView blockTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVCardName);
            mobileTV = itemView.findViewById(R.id.idTVCardMobile);
            blockTV = itemView.findViewById(R.id.idTVCardBlock);
        }
    }
}

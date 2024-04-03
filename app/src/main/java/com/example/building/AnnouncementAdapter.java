package com.example.building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>{

    private Context context;
    private static ArrayList<AnnouncementModel> courseModelArrayList;

    public AnnouncementAdapter(Context context, ArrayList<AnnouncementModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }
    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_card, parent, false);
        return new AnnouncementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        AnnouncementModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getType().toUpperCase());
        holder.cardBlockTV.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardNameTV;
        private final TextView cardBlockTV;
        private ManagerAdapter.OnItemClickListener listener;
        private ManagerAdapter.OnLongClickListener long_listener;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardNameTV = itemView.findViewById(R.id.announcementName);
            cardBlockTV = itemView.findViewById(R.id.announcementDesc);
        }
    }
}

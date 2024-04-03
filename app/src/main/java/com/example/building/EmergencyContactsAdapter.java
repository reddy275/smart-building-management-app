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

public class EmergencyContactsAdapter extends RecyclerView.Adapter<EmergencyContactsAdapter.ViewHolder>{

    private Context context;
    private static ArrayList<EmergencyContactModel> courseModelArrayList;
    public EmergencyContactsAdapter(Context context, ArrayList<EmergencyContactModel> courseModelArrayList) {
        this.context = context;
        EmergencyContactsAdapter.courseModelArrayList = courseModelArrayList;
    }
    @NonNull
    @Override
    public EmergencyContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_card, parent, false);
        return new EmergencyContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyContactsAdapter.ViewHolder holder, int position) {

        EmergencyContactModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getContactName());

        holder.cardBlockTV.setText(model.getContactNumber());
        if(model.getContactName().toLowerCase().contains("police")) {
            holder.cardIV.setImageResource(R.drawable.police_badge);
        } else if(model.getContactName().toLowerCase().contains("security")) {
            holder.cardIV.setImageResource(R.drawable.security);
        } else if(model.getContactName().toLowerCase().contains("woman")) {
            holder.cardIV.setImageResource(R.drawable.woman_helpline);
        } else if(model.getContactName().toLowerCase().contains("fire")) {
            holder.cardIV.setImageResource(R.drawable.fire);
        } else {
            holder.cardIV.setImageResource(R.drawable.apartment);
        }

    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cardIV;
        private final TextView cardNameTV;
        private final TextView cardBlockTV;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardIV = itemView.findViewById(R.id.managerIV);
            cardNameTV = itemView.findViewById(R.id.managerTVName);
            cardBlockTV = itemView.findViewById(R.id.managerTVBlock);

            final Context context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}

package com.example.building;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComplaintManagerAdapter extends RecyclerView.Adapter<ComplaintManagerAdapter.ViewHolder>{

    private static Context context;
    private static ArrayList<ComplaintModel> courseModelArrayList;
    private DBHelper db;

    public interface OnItemClickListener {
        void onItemClick(ComplaintModel b);
    }

    private ComplaintManagerAdapter.OnItemClickListener listener;

    // Set the listener
    public void setOnItemClickListener(ComplaintManagerAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ComplaintManagerAdapter(Context context, ArrayList<ComplaintModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public ComplaintManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);
        return new ComplaintManagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintManagerAdapter.ViewHolder holder, int position) {
        db  = new DBHelper(context);

        ComplaintModel model = courseModelArrayList.get(position);
        String username = db.getUsernameFromUserId(model.getIssuerID());
        holder.cardNameTV.setText("Name: "+username);
        holder.cardBlock.setText("Block: "+db.getBlockNameById((int) model.getBlockID()));
        holder.cardFloor.setText("Floor: "+model.getFloor());
        holder.cardIssue.setText("Issue: " +model.getIssueType());
        holder.cardDate.setText("Date: "+model.getDateOfReport());
        holder.cardStatus.setText("Status: "+model.getStatus());

        SharedPreferences preferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        if ("completed".equalsIgnoreCase(model.getStatus()) || preferences.getString("designation", "").equalsIgnoreCase("Admin")) {
            holder.showIssue.setVisibility(View.GONE);
        } else {
            holder.showIssue.setVisibility(View.VISIBLE);
        }

        if ("completed".equalsIgnoreCase(model.getStatus()) && preferences.getString("designation", "").equalsIgnoreCase("Manager")) {
            holder.rb.setVisibility(View.VISIBLE);
            holder.rb.setNumStars(5);
            holder.rb.setMax(5);
            holder.rb.setRating(db.getOverallAverageRating(model.getComplaintID()));
        }
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView showIssue;
        private final TextView cardNameTV;
        private TextView cardBlock, cardFloor, cardIssue, cardDate, cardStatus;
        private RatingBar rb;

        private ComplaintManagerAdapter.OnItemClickListener listener;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardNameTV = itemView.findViewById(R.id.issuerName);
            cardBlock = itemView.findViewById(R.id.issueBlock);
            cardFloor = itemView.findViewById(R.id.issueFloor);
            cardIssue = itemView.findViewById(R.id.issueType);
            cardDate = itemView.findViewById(R.id.issueDate);
            cardStatus = itemView.findViewById(R.id.issueStatus);

            showIssue = itemView.findViewById(R.id.showIssue);
            rb = itemView.findViewById(R.id.overallRating);
            rb.setVisibility(View.GONE);

            showIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ComplaintModel issueDetails = courseModelArrayList.get(position);
                        DBHelper db=new DBHelper(v.getContext());
                        IssueDetails fragment = new IssueDetails();
                        // Pass the selected ComplaintModel details to the fragment
                        Bundle bundle = new Bundle();
                        bundle.putInt("complaintID", issueDetails.getComplaintID());
                        bundle.putInt("userID", issueDetails.getIssuerID());
                        bundle.putString("block", db.getBlockNameById((int) issueDetails.getBlockID()));
                        bundle.putString("floor", issueDetails.getFloor());
                        bundle.putString("doorNo", issueDetails.getDoorNo());
                        bundle.putString("issueType", issueDetails.getIssueType());
                        bundle.putString("description", issueDetails.getIssueDescription());
                        bundle.putString("repeated", issueDetails.getRepeatedIssue());
                        bundle.putString("work", issueDetails.getWorkRequirement());
                        bundle.putString("dateOfReport", issueDetails.getDateOfReport());
                        bundle.putString("phoneNo", issueDetails.getPhoneNumber());
                        // Add other necessary details...
                        fragment.setArguments(bundle);

                        // Use a FragmentManager to open the fragment
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.commit();
                    }
                }
            });
        }
    }
}

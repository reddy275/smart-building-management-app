package com.example.building;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComplaintUserAdapter extends RecyclerView.Adapter<ComplaintUserAdapter.ViewHolder>{

    private Context context;
    private static ArrayList<ComplaintModel> courseModelArrayList;
    private static DBHelper db;

    public ComplaintUserAdapter(Context context, ArrayList<ComplaintModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }
    @NonNull
    @Override
    public ComplaintUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_user_card, parent, false);
        return new ComplaintUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintUserAdapter.ViewHolder holder, int position) {
        db  = new DBHelper(context);

        ComplaintModel model = courseModelArrayList.get(position);
        holder.cardIssue.setText("Issue: " +model.getIssueType());
        holder.cardDate.setText("Date: "+model.getDateOfReport());
        holder.cardStatus.setText("Status: "+model.getStatus());

        Log.d("IssueTypeDebug", "IssueType: " + model.getIssueType());

        if(model.getStatus().equalsIgnoreCase("Completed")) {
            holder.cancelBtn.setVisibility(View.GONE);
            holder.completedBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView cardIssue, cardDate, cardStatus;
        AppCompatButton completedBtn, cancelBtn;
        ImageView showIssue;

        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            cardIssue = itemView.findViewById(R.id.issueType);
            cardDate = itemView.findViewById(R.id.issueDate);
            cardStatus = itemView.findViewById(R.id.issueStatus);
            showIssue = itemView.findViewById(R.id.showIssue);

            completedBtn = itemView.findViewById(R.id.completedIssueBtn);

            cancelBtn = itemView.findViewById(R.id.cancelledissueBtn);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ComplaintModel model = courseModelArrayList.get(position);
                        int complaintID = model.getComplaintID();

                        // Delete the complaint from the database
                        boolean deletionSuccessful = db.deleteComplaint(complaintID);

                        if (deletionSuccessful) {
                            // Removal from the ArrayList and notification to the adapter
                            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                            courseModelArrayList.remove(position);
                            notifyDataSetChanged();
                        } else {
                            // Handle the case where deletion failed (show a message, log, etc.)
                            Toast.makeText(context, "Error! Please try again.", Toast.LENGTH_LONG).show();

                        }

                        // Remove the complaint from the ArrayList


                        // Notify the adapter that the data set has changed
                        notifyDataSetChanged();
                    }
                }
            });

            completedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ComplaintModel model = courseModelArrayList.get(position);
                        int complaintID = model.getComplaintID();

                        ComplaintReviewPage fragment = new ComplaintReviewPage();

                        // Pass complaintID to the fragment
                        Bundle args = new Bundle();
                        args.putInt("complaintID", complaintID);
                        fragment.setArguments(args);

                        // Replace the current fragment with ComplaintReviewPage
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment) // Replace R.id.fragment_container with the ID of your fragment container
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });


        }
    }
}

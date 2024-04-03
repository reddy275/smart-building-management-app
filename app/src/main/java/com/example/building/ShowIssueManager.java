package com.example.building;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowIssueManager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowIssueManager extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DBHelper db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowIssueManager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowIssueManager.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowIssueManager newInstance(String param1, String param2) {
        ShowIssueManager fragment = new ShowIssueManager();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_issue_manager, container, false);
        RecyclerView cardsRV = view.findViewById(R.id.idRVComplaints);
        db = new DBHelper(getActivity());

        ArrayList<ComplaintModel> blockCardModelArrayList = new ArrayList<ComplaintModel>();
        TextView heading = view.findViewById(R.id.mainHeadingJobs);

        Bundle args = getArguments();
        SharedPreferences preferences = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int blockID = preferences.getInt("blockID", 0);
        blockCardModelArrayList = (ArrayList<ComplaintModel>) db.getComplaintsByBlockID(blockID);
        ArrayList<ComplaintModel> filteredList = new ArrayList<>();

        if (args != null) {
            if(args.getString("from").equalsIgnoreCase("jobPage")) {
                heading.setText("Jobs");
            }
            Log.d("Arguments",args.getString("status"));
            if(args.getString("status").equals("Pending")) {

                filteredList = (ArrayList<ComplaintModel>) blockCardModelArrayList.stream()
                        .filter(complaint -> "Pending".equalsIgnoreCase(complaint.getStatus()))
                        .collect(Collectors.toList());

            } else if(args.getString("status").equals("Completed")) {

                filteredList = (ArrayList<ComplaintModel>) blockCardModelArrayList.stream()
                        .filter(complaint -> "Completed".equalsIgnoreCase(complaint.getStatus()))
                        .collect(Collectors.toList());
            }
        } else {
            heading.setText("Issue List");
            if(!preferences.getString("designation","").equalsIgnoreCase("Admin")) {
                filteredList = (ArrayList<ComplaintModel>) blockCardModelArrayList.stream()
                        .filter(complaint -> "pending".equalsIgnoreCase(complaint.getStatus()))
                        .collect(Collectors.toList());
            } else {
                filteredList = (ArrayList<ComplaintModel>) db.getComplaints();
            }

        }

        ComplaintManagerAdapter courseAdapter = new ComplaintManagerAdapter(view.getContext(), filteredList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);
        return view;
    }
}
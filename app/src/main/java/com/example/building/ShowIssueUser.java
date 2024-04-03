package com.example.building;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowIssueUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowIssueUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DBHelper db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowIssueUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowIssueUser.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowIssueUser newInstance(String param1, String param2) {
        ShowIssueUser fragment = new ShowIssueUser();
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
        View view = inflater.inflate(R.layout.fragment_show_issue_user, container, false);
        RecyclerView cardsRV = view.findViewById(R.id.idRVComplaints);
        db = new DBHelper(getActivity());

        SharedPreferences preferences = requireActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int userID = preferences.getInt("userID", 0);

        // Here, we have created new array list and added data to it
        ArrayList<ComplaintModel> blockCardModelArrayList = new ArrayList<ComplaintModel>();


        blockCardModelArrayList = (ArrayList<ComplaintModel>) db.getComplaintsByUserID(userID);

        // we are initializing our adapter class and passing our arraylist to it.
        ComplaintUserAdapter courseAdapter = new ComplaintUserAdapter(view.getContext(), blockCardModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);


        return view;
    }
}
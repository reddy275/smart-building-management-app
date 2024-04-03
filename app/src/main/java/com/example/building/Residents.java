package com.example.building;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Residents extends Fragment {

    private DBHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_residents, container, false);

        RecyclerView cardsRV = view.findViewById(R.id.idRVResidentCards);

        db = new DBHelper(requireContext());

        // Here, we have created new array list and added data to it
        ArrayList<ArrayList<String>> CardModelArrayList = new ArrayList<>();
        CardModelArrayList = db.getAllUsersDetailsWithBlockName();

//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 1));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 2));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 3));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 4));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 5));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 6));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 7));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 1));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 2));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 3));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 4));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 5));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Sarvanan", "Mobile No: 9876543210", 6));
//        CardModelArrayList.add(new ResidentCardsModel("Name: Moni", "Mobile No: 9876543210", 7));

        // we are initializing our adapter class and passing our arraylist to it.
        ResidentCardsAdapter courseAdapter = new ResidentCardsAdapter(getContext(), CardModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);

        return view;
    }
}
package com.example.building;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SmartBuildAdminHome2 extends Fragment {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_build_admin_home2);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_smart_build_admin_home2, container, false);

        RecyclerView cardsRV = view.findViewById(R.id.idRVCards);

        // Here, we have created new array list and added data to it
        ArrayList<CardModel> CardModelArrayList = new ArrayList<CardModel>();

        SharedPreferences preferences = requireActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "User");

        if(userDesignation.equalsIgnoreCase("admin")) {
        CardModelArrayList.add(new CardModel("Facade", R.drawable.house));
        CardModelArrayList.add(new CardModel("Blocks", R.drawable.apartment));
        //CardModelArrayList.add(new CardModel("Landscaping and Gardening", R.drawable.park));
        CardModelArrayList.add(new CardModel("Security Features", R.drawable.home_security));
        CardModelArrayList.add(new CardModel("Fire Safety", R.drawable.fire_extinguisher));
        CardModelArrayList.add(new CardModel("Parking Area", R.drawable.parking_area));
        CardModelArrayList.add(new CardModel("Residents", R.drawable.architect));
        } else if(userDesignation.equalsIgnoreCase("manager")) {

        } else {
            CardModelArrayList.add(new CardModel("Events", R.drawable.events));
            CardModelArrayList.add(new CardModel("Announcements and NEWS", R.drawable.announcement));
            CardModelArrayList.add(new CardModel("Compliant / Feedback", R.drawable.complaint));
            CardModelArrayList.add(new CardModel("Emergency Contacts", R.drawable.emergency_contact));
        }
        // we are initializing our adapter class and passing our arraylist to it.
        CardAdapter courseAdapter = new CardAdapter(getContext(), CardModelArrayList);
        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        courseAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(String name) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (name.equals("Blocks")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new BlocksActivity(), "Block Fragment");
                } else if (name.equals("Landscaping and Gardening")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new LandscapingGardening());
                } else if (name.equals("Security Features")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new SecurityFeatures());
                } else if (name.equals("Fire Safety")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new FireSafety());
                } else if (name.equals("Parking Area")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new ParkingArea());
                } else if (name.equals("Residents")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new Residents());
                } else if (name.equals("Events")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new EventsList());
                } else if (name.equals("Announcements and NEWS")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new AnnouncementsList());
                } else if (name.equals("Compliant / Feedback")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new AddComplaint());
                } else if (name.equals("Emergency Contacts")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new EmergencyContactList());
                } else if(name.equalsIgnoreCase("Facade")) {
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
                    fragmentTransaction.add(R.id.fragment_container, new Facade());
                }

                fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
                fragmentTransaction.commit();
            }
        });

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);
        cardsRV.setClickable(true);
        return view;
    }
}
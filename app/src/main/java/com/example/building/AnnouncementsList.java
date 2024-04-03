package com.example.building;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnnouncementsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHelper db;

    public AnnouncementsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementsList.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementsList newInstance(String param1, String param2) {
        AnnouncementsList fragment = new AnnouncementsList();
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
        View view = inflater.inflate(R.layout.fragment_announcements_list, container, false);

        RecyclerView cardsRV = view.findViewById(R.id.idRVAnnouncements);
        db = new DBHelper(getActivity());

        // Here, we have created new array list and added data to it
        ArrayList<AnnouncementModel> CardModelArrayList = (ArrayList<AnnouncementModel>) db.getAnnouncements();

        // we are initializing our adapter class and passing our arraylist to it.
        AnnouncementAdapter courseAdapter = new AnnouncementAdapter(getContext(), CardModelArrayList);
        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);
        cardsRV.setClickable(true);
        return view;
    }
}
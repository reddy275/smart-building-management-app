package com.example.building;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAnnouncement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAnnouncement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner spinnerAnnouncementType;
    private EditText eventDesc;
    private Button addAnnouncementBtn;
    private DBHelper db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAnnouncement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAnnouncement.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAnnouncement newInstance(String param1, String param2) {
        AddAnnouncement fragment = new AddAnnouncement();
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
        View view = inflater.inflate(R.layout.fragment_add_announcement, container, false);

        db = new DBHelper(requireActivity());

        spinnerAnnouncementType = view.findViewById(R.id.spinnerAnnouncementType);
        eventDesc = view.findViewById(R.id.eventDesc);
        addAnnouncementBtn = view.findViewById(R.id.addEventBtn);

        addAnnouncementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to add the announcement to the database
                addAnnouncement();
            }
        });

        return view;
    }

    private void addAnnouncement() {
        // Retrieve input values
        String announcementType = spinnerAnnouncementType.getSelectedItem().toString();
        String description = eventDesc.getText().toString();

        // Validate input values if needed

        // Call the method in DBHelper to add the announcement
        long result = db.insertAnnouncement(announcementType, description);

        // Handle the result as needed
        if (result != -1) {
            Intent intent=new Intent(getContext(),SmartBuildAdminMenu.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(requireContext(), "Announcement added successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to add announcement", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.building;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddComplaint extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DBHelper db;

    List<String> blockNamesList;
    List<Long> blockNameIDs;
    private long selectedBlockID;

    private Spinner complaintBlockSpinner;
    private Spinner doorNoSpinner;
    private Spinner floorSpinner,complaintTypeSpinner, repeatedIssueSpinner, workRequirementSpinner;

    // Assume these are your lists for spinner entries
    private List<String> floorNumberList;
    private EditText issueDescET, phoneNumberET;

    private Button submitBtn;
    private List<String> doorNumbersList;
    public AddComplaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddComplaint.
     */
    // TODO: Rename and change types and number of parameters
    public static AddComplaint newInstance(String param1, String param2) {
        AddComplaint fragment = new AddComplaint();
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
        View view = inflater.inflate(R.layout.fragment_add_complaint, container, false);

        // Initialize your spinners
        complaintBlockSpinner = view.findViewById(R.id.complaintBlockSpinner);
        floorSpinner = view.findViewById(R.id.complaintFloorSpinner);
        doorNoSpinner = view.findViewById(R.id.doorNoSpinner);

        blockNamesList = new ArrayList<>();
        blockNameIDs = new ArrayList<>();
        // Initialize your lists
        getBlockNames();
        floorNumberList = getFloors();
        // Implement getDoorNumbers() to fetch door numbers

        ArrayAdapter<String> blockAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, blockNamesList);
        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintBlockSpinner.setAdapter(blockAdapter);

        complaintBlockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedBlockID = blockNameIDs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // Set the adapter for the first spinner (complaintBlockSpinner)
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, floorNumberList);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(floorAdapter);

        // Set the listener for the first spinner (complaintBlockSpinner)
        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected block
                String selectedFloor = floorNumberList.get(position);

                // Set the adapter for the second spinner (doorNoSpinner) based on the selected block
                ArrayAdapter<String> doorNoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getDoorNumbersForBlock(selectedFloor));
                doorNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doorNoSpinner.setAdapter(doorNoAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

         issueDescET = view.findViewById(R.id.issueDescET);
         phoneNumberET = view.findViewById(R.id.complaintPhoneNumberET);
         complaintTypeSpinner = view.findViewById(R.id.complaintTypeSpinner);
         repeatedIssueSpinner = view.findViewById(R.id.repeatedIssueSpinner);
         workRequirementSpinner = view.findViewById(R.id.workRequirementSpinner);

        // Get selected values from Spinners

        submitBtn = view.findViewById(R.id.complaintSubmitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to add the event to the database
                addComplaint();
            }
        });




        return view;
    }
    private void getBlockNames() {
        db = new DBHelper(getActivity());
        List<BlockCardModel> blocks = db.getAllBlocks();

        for (BlockCardModel block : blocks) {
            blockNamesList.add(block.getBlock());
            blockNameIDs.add(block.getId());
        }
    }
    private List<String> getFloors() {
        List<String> floorNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            floorNumbers.add(String.valueOf(i));
        }
        return floorNumbers;
    }


    // Sample door numbers for a given block
    private List<String> getDoorNumbersForBlock(String floor) {
        List<String> doorNumbers = new ArrayList<>();
        int floorNumber = Integer.parseInt(floor);
        for (int i = 1; i <= 10; i++) {
            doorNumbers.add(String.valueOf(floorNumber * 100 + i));
        }
        return doorNumbers;
    }

    private void addComplaint() {
        String selectedComplaintType = complaintTypeSpinner.getSelectedItem().toString();
        String selectedRepeatedIssue = repeatedIssueSpinner.getSelectedItem().toString();
        String selectedWorkRequirement = workRequirementSpinner.getSelectedItem().toString();
        // Get values from EditText
        String issueDescription = issueDescET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();

        SharedPreferences preferences = requireActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int userID = preferences.getInt("userID", 0);

        // Assume you have already initialized complaintBlockSpinner, floorSpinner, and doorNoSpinner

        // Get selected values from Spinners
        String selectedBlock = complaintBlockSpinner.getSelectedItem().toString();
        String selectedFloor = floorSpinner.getSelectedItem().toString();
        String selectedDoorNo = doorNoSpinner.getSelectedItem().toString();

        // Create a ComplaintModel object with the obtained values
        ComplaintModel complaint = new ComplaintModel();
        complaint.setBlockID(selectedBlockID);
        complaint.setFloor(selectedFloor);
        complaint.setDoorNo(selectedDoorNo);
        complaint.setIssueType(selectedComplaintType);
        complaint.setRepeatedIssue(selectedRepeatedIssue);
        complaint.setWorkRequirement(selectedWorkRequirement);
        complaint.setPhoneNumber(phoneNumber);
        complaint.setIssueDescription(issueDescription);
        complaint.setIssuerID(userID);
        complaint.setStatus("Pending");

        // Insert the complaint into the database
        long complaintId = db.insertComplaint(complaint);

        // Display a message or perform any other action based on the result
        if (complaintId != -1) {
            Intent intent=new Intent(getContext(),SmartBuildAdminMenu.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(requireContext(), "Complaint added successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to add complaint", Toast.LENGTH_SHORT).show();
        }
    }
}
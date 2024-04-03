package com.example.building;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssueDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DBHelper db;
    private int complaintID;

    public IssueDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssueDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static IssueDetails newInstance(String param1, String param2) {
        IssueDetails fragment = new IssueDetails();
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
        View view = inflater.inflate(R.layout.fragment_issue_details, container, false);

        TextView textName = view.findViewById(R.id.textName);
        TextView textBlock = view.findViewById(R.id.textBlock);
        TextView textFloor = view.findViewById(R.id.textFloor);
        TextView textDoorNo = view.findViewById(R.id.textDoorNo);
        TextView textIssueType = view.findViewById(R.id.textIssueType);
        TextView textIssueDescription = view.findViewById(R.id.textIssueDescription);
        TextView textRepeatedIssue = view.findViewById(R.id.textRepeatedIssue);
        TextView textWorkRequirement = view.findViewById(R.id.textWorkRequirement);
        TextView textDateOfReport = view.findViewById(R.id.textDateOfReport);
        TextView textPhoneNumber = view.findViewById(R.id.textPhoneNumber);
        Button takeToStaff = view.findViewById(R.id.takeToStaff);

        // Get reference to ImageView
        ImageView displayImage = view.findViewById(R.id.displayImage);

        Bundle args = getArguments();
        db = new DBHelper(getActivity());

        if (args != null) {
            int userID = args.getInt("userID", 0);
            String block = args.getString("block", "");
            String floor = args.getString("floor", "");
            String doorNo = args.getString("doorNo", "");
            String issueType = args.getString("issueType", "");
            String issueDescription = args.getString("description", "");
            String repeatedIssue = args.getString("repeated", "");
            String workRequirement = args.getString("work", "");
            String dateOfReport = args.getString("dateOfReport", "");
            String phoneNumber = args.getString("phoneNo", "");
            complaintID = args.getInt("complaintID", 0);
            // Set data to TextViews
            textName.setText("Name: " + db.getUsernameFromUserId(userID));
            textBlock.setText("Block: " + block);
            textFloor.setText("Floor: " + floor);
            textDoorNo.setText("Door No: " + doorNo);
            textIssueType.setText("Issue Type: " + issueType);
            textIssueDescription.setText("Issue Description: " + issueDescription);
            textRepeatedIssue.setText("Repeated Issue: " + repeatedIssue);
            textWorkRequirement.setText("Work Requirement: " + workRequirement);
            textDateOfReport.setText("Date of Report: " + dateOfReport);
            textPhoneNumber.setText("Phone Number: " + phoneNumber);

            if(issueType.toLowerCase().contains("plumb")) {
                displayImage.setImageResource(R.drawable.plumber);
            } else if(issueType.toLowerCase().contains("electr")) {
                displayImage.setImageResource(R.drawable.electrician);
            } else if(issueType.toLowerCase().contains("paint")) {
                displayImage.setImageResource(R.drawable.painter);
            } else if(issueType.toLowerCase().contains("carpent")) {
                displayImage.setImageResource(R.drawable.carpenter);
            } else {
                displayImage.setImageResource(R.drawable.plumber);
            }

        }

        takeToStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("complaintID", complaintID);
                bundle.putBoolean("assign", true);

                StaffList fragment = new StaffList();

                fragment.setArguments(bundle);

                // Use a FragmentManager to open the fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null); // Add to back stack if needed
                transaction.commit();
            }
        });
        return view;
    }
}
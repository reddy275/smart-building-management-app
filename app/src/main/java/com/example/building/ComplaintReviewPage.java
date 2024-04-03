package com.example.building;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintReviewPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintReviewPage extends Fragment {

    private int complaintID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComplaintReviewPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintReviewPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintReviewPage newInstance(String param1, String param2) {
        ComplaintReviewPage fragment = new ComplaintReviewPage();
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
            complaintID = getArguments().getInt("complaintID", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_review_page, container, false);

        // Retrieve values from the XML layout
        Button submitBtn = view.findViewById(R.id.submitReviewBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar professionalismRatingBar = view.findViewById(R.id.professionalismRatingBar);
                RatingBar qualityOfWorkRatingBar = view.findViewById(R.id.qualityOfWorkRatingBar);
                RatingBar communicationRatingBar = view.findViewById(R.id.communicationRatingBar);
                RatingBar punctualityRatingBar = view.findViewById(R.id.punctualityRatingBar);
                EditText commentsEditText = view.findViewById(R.id.eventDesc);

                // Get the values from the RatingBars
                float professionalismRating = professionalismRatingBar.getRating();
                float qualityOfWorkRating = qualityOfWorkRatingBar.getRating();
                float communicationRating = communicationRatingBar.getRating();
                float punctualityRating = punctualityRatingBar.getRating();
                String comments = commentsEditText.getText().toString();

                // Update the Complaints table with status "Completed"


                // Update the Reviews table with the details
                addReview(complaintID, professionalismRating, qualityOfWorkRating, communicationRating, punctualityRating, comments);
            }
        });

        return view;
    }

    private void addReview(int complaintID, float professionalism, float qualityOfWork, float communication, float punctuality, String comments) {
        DBHelper dbHelper = new DBHelper(requireContext()); // Initialize your DBHelper
        long insertionSuccessful = dbHelper.insertReview(complaintID, professionalism, qualityOfWork, communication, punctuality, comments);

        if (insertionSuccessful != -1) {
            boolean updateSuccessful = dbHelper.updateComplaintStatus(complaintID, "Completed");

            if(updateSuccessful) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ShowIssueUser())
                        .commit();

                Toast.makeText(getContext(), "Issue closed", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Error! Please try again", Toast.LENGTH_LONG).show();
        }
    }
}
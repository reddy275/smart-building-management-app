package com.example.building;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEmergencyContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEmergencyContact extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView addBlockHeading;
    private EditText contactNameET, contactNumberET;
    private Button addContactBtn;
    private DBHelper dbHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEmergencyContact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEmergencyContact.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEmergencyContact newInstance(String param1, String param2) {
        AddEmergencyContact fragment = new AddEmergencyContact();
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
        View view = inflater.inflate(R.layout.fragment_add_emergency_contact, container, false);

        dbHelper = new DBHelper(requireActivity());

        contactNameET = view.findViewById(R.id.contactName);
        contactNumberET = view.findViewById(R.id.eventDesc);
        addContactBtn = view.findViewById(R.id.addEventBtn);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to add the emergency contact to the database
                addEmergencyContact();
            }
        });
        return view;
    }

    private void addEmergencyContact() {
        // Retrieve input values
        String contactName = contactNameET.getText().toString();
        String contactNumber = contactNumberET.getText().toString();

        // Validate input values if needed

        // Call the method in EmergencyContactDBHelper to add the emergency contact
        long result = dbHelper.insertContact(contactName, contactNumber);

        // Handle the result as needed
        if (result != -1) {
            Intent intent=new Intent(getContext(),SmartBuildAdminMenu.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(requireContext(), "Contact added successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to add contact", Toast.LENGTH_SHORT).show();
        }
    }
}
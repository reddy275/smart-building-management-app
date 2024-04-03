package com.example.building;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class AddBlock extends Fragment {

    private EditText blockEditText;
    private EditText blockNameEditText;
    private Spinner parkingAvailabilitySpinner;
    private Spinner furnishedSpinner;
    private Button submitButton;

    // Database helper instance
    private DBHelper databaseHelper;

    private Button editButton;
    private Button updateButton;

    private boolean isEditMode = false;

    private long blockID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_block, container, false);

        blockEditText = view.findViewById(R.id.editTextBlock);
        blockNameEditText = view.findViewById(R.id.editTextBlockName);
        parkingAvailabilitySpinner = view.findViewById(R.id.spinnerParkingAvailability);
        furnishedSpinner = view.findViewById(R.id.spinnerFurnished);
        submitButton = view.findViewById(R.id.buttonSubmit);

        // Initialize database helper
        databaseHelper = new DBHelper(getActivity());

        // Set up spinner adapters
        ArrayAdapter<CharSequence> parkingAvailabilityAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.parking_availability_options,
                android.R.layout.simple_spinner_item
        );
        parkingAvailabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parkingAvailabilitySpinner.setAdapter(parkingAvailabilityAdapter);

        ArrayAdapter<CharSequence> furnishedAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.furnished_options,
                android.R.layout.simple_spinner_item
        );
        furnishedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        furnishedSpinner.setAdapter(furnishedAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndInsert();
            }
        });
        editButton = view.findViewById(R.id.buttonEdit);
        updateButton = view.findViewById(R.id.buttonUpdate);


        Bundle args = getArguments();
        if (args != null) {
            editButton.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.INVISIBLE);
            blockID = args.getLong("blockID");
            String block = args.getString("block", "");
            String blockName = args.getString("blockName", "");
            boolean parkingAvailability = args.getBoolean("parkingAvailability", false);
            boolean furnished = args.getBoolean("furnished", false);

            // Set values to the fields
            blockEditText.setText(block);
            blockNameEditText.setText(blockName);

            // Set spinner selections based on boolean values
            parkingAvailabilitySpinner.setSelection(parkingAvailability ? 0 : 1);
            furnishedSpinner.setSelection(furnished ? 0 : 1);

            // Disable fields
            blockEditText.setEnabled(false);
            blockNameEditText.setEnabled(false);
            parkingAvailabilitySpinner.setEnabled(false);
            furnishedSpinner.setEnabled(false);
            submitButton.setVisibility(View.GONE);  // Optional: Hide the submit button
        }




        // Set up click listeners for buttons
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableAllFields();
                editButton.setVisibility(View.INVISIBLE);
                updateButton.setVisibility(View.VISIBLE);
                isEditMode = true;
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String block = blockEditText.getText().toString().trim();
                String blockName = blockNameEditText.getText().toString().trim();
                String parkingAvailability = parkingAvailabilitySpinner.getSelectedItem().toString();
                String furnished = furnishedSpinner.getSelectedItem().toString();
                int rowsAffected = databaseHelper.updateBlock(blockID,block,blockName,parkingAvailability.equalsIgnoreCase("Yes"),furnished.equalsIgnoreCase("Yes"));
                if (rowsAffected > 0) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container, new BlocksActivity());
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Block updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Update failed
                    Toast.makeText(getActivity(), "Failed to update block", Toast.LENGTH_SHORT).show();
                }
                updateButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
                isEditMode = false;
            }
        });

        return view;
    }

    private void validateAndInsert() {
        // Get values from the form
        String block = blockEditText.getText().toString().trim();
        String blockName = blockNameEditText.getText().toString().trim();
        String parkingAvailability = parkingAvailabilitySpinner.getSelectedItem().toString();
        String furnished = furnishedSpinner.getSelectedItem().toString();

        // Validate the form
        if (block.isEmpty() || blockName.isEmpty()) {
            // Handle validation error
            Toast.makeText(getActivity(),"Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert spinner selections to boolean
        boolean parkingAvailable = parkingAvailability.equalsIgnoreCase("Yes");
        boolean isFurnished = furnished.equalsIgnoreCase("Yes");

        // Insert data into the database
        long result = databaseHelper.insertBlock(block, blockName, parkingAvailable, isFurnished);

        if (result != -1) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, new BlocksActivity());
            fragmentTransaction.commit();

            Toast.makeText(getActivity(), "Added block: "+ block, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getActivity(), "Block "+ block +" already exists!", Toast.LENGTH_LONG).show();

        }
    }


    private void enableAllFields() {
        // Enable all fields here (EditTexts, Spinners, etc.)
        blockEditText.setEnabled(true);
        blockNameEditText.setEnabled(true);
        parkingAvailabilitySpinner.setEnabled(true);
        furnishedSpinner.setEnabled(true);
    }

    private void disableAllFields() {
        // Disable all fields here (EditTexts, Spinners, etc.)
        blockEditText.setEnabled(false);
        blockNameEditText.setEnabled(false);
        parkingAvailabilitySpinner.setEnabled(false);
        furnishedSpinner.setEnabled(false);
    }
}

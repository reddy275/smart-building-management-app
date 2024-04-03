package com.example.building;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddManager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddManager extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DBHelper db;

    private EditText editTextName, editTextDesignation, editTextPhoneNumber, editTextJoiningDate;
    private Spinner blockSpinner, nationalitySpinner;
    private Button buttonSubmit, buttonManagerEdit, buttonManagerUpdate;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar calendar;
    private long managerID;

    List<String> blockNamesList;
    List<Long> blockNameIDs;
    private long selectedBlockID;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddManager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddManager.
     */
    // TODO: Rename and change types and number of parameters
    public static AddManager newInstance(String param1, String param2) {
        AddManager fragment = new AddManager();
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
        View view = inflater.inflate(R.layout.fragment_add_manager, container, false);

        // Initialize UI elements
        editTextName = view.findViewById(R.id.editTextName);
        editTextDesignation = view.findViewById(R.id.editTextDesignation);
        editTextPhoneNumber = view.findViewById(R.id.managerPhoneNumberET);
        editTextJoiningDate = view.findViewById(R.id.joiningDatePickerId);
        blockSpinner = view.findViewById(R.id.spinnerBlock);
        nationalitySpinner = view.findViewById(R.id.managerNationalitySpinner);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonManagerEdit = view.findViewById(R.id.buttonManagerEdit);
        buttonManagerUpdate = view.findViewById(R.id.buttonManagerUpdate);
        editTextDesignation.setEnabled(false);
        // Set click listeners
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addManager();
            }
        });

        blockNamesList = new ArrayList<>();
        blockNameIDs = new ArrayList<>();

        getBlockNames();

        ArrayAdapter<String> blockAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, blockNamesList);
        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blockSpinner.setAdapter(blockAdapter);

        blockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedBlockID = blockNameIDs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Bundle args = getArguments();
        if (args != null) {
            buttonManagerEdit.setVisibility(View.VISIBLE);
            buttonManagerUpdate.setVisibility(View.INVISIBLE);
            managerID = args.getLong("managerID");
            String name = args.getString("name", "");
            String designation = args.getString("designation", "");
            String phoneNumber = args.getString("phoneNumber", "");
            String block = args.getString("block", "");
            String nationality = args.getString("nationality", "");
            String joiningDate = args.getString("joiningDate", "");

            // Set values to the fields
            editTextName.setText(name);
            editTextDesignation.setText(designation);
            editTextPhoneNumber.setText(phoneNumber);
            // Set spinner selections based on values
            blockSpinner.setSelection(getIndex(blockSpinner, block));
            nationalitySpinner.setSelection(getIndex(nationalitySpinner, nationality));
            editTextJoiningDate.setText(joiningDate);

            // Disable fields
            editTextName.setEnabled(false);
            editTextDesignation.setEnabled(false);
            editTextPhoneNumber.setEnabled(false);
            blockSpinner.setEnabled(false);
            nationalitySpinner.setEnabled(false);
            editTextJoiningDate.setEnabled(false);
            buttonSubmit.setVisibility(View.GONE);  // Optional: Hide the submit button
        }




        buttonManagerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enable editing of fields
                enableEditing();
            }
        });

        buttonManagerUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update staff details in the database
                updateManager();
            }
        });

        // Set up date picker
        calendar = Calendar.getInstance();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        editTextJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        requireActivity(),
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        return view;
    }

    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }

    private void updateDate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editTextJoiningDate.setText(sdf.format(calendar.getTime()));
    }

    private void addManager() {
        // Get values from UI elements
        String name = editTextName.getText().toString().trim();
        String designation = editTextDesignation.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        long block = selectedBlockID;
        String nationality = nationalitySpinner.getSelectedItem().toString();
        String joiningDate = editTextJoiningDate.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(joiningDate)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        // Validate input (You can add more validation as needed)

        // Insert staff into the database
        DBHelper dbHelper = new DBHelper(requireActivity());
        long result = dbHelper.insertManager(name, joiningDate, block, phoneNumber, nationality);


        if (result != -1) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, new ManagerList());
            fragmentTransaction.commit();
            Toast.makeText(requireContext(), "Manager added successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to add manager", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateManager() {
        String name = editTextName.getText().toString().trim();
        String designation = editTextDesignation.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        long block = selectedBlockID;
        String nationality = nationalitySpinner.getSelectedItem().toString();
        String joiningDate = editTextJoiningDate.getText().toString().trim();

        // Validate input (You can add more validation as needed)

        // Update staff in the database
        DBHelper dbHelper = new DBHelper(requireActivity());
        int success = dbHelper.updateManager(managerID, name, joiningDate, block, phoneNumber, nationality);

        if (success != -1) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, new ManagerList());
            fragmentTransaction.commit();
            Toast.makeText(requireContext(), "Manager updated successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to update manager", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableEditing() {
        // Enable editing of fields
        editTextName.setEnabled(true);
        editTextPhoneNumber.setEnabled(true);
        blockSpinner.setEnabled(true);
        nationalitySpinner.setEnabled(true);
        editTextJoiningDate.setEnabled(true);

        // Make Edit button invisible and Update button visible
        buttonManagerEdit.setVisibility(View.GONE);
        buttonManagerUpdate.setVisibility(View.VISIBLE);
    }

    private void getBlockNames() {
        db = new DBHelper(getActivity());
        List<BlockCardModel> blocks = db.getAllBlocks();

        for (BlockCardModel block : blocks) {
            blockNamesList.add(block.getBlock());
            blockNameIDs.add(block.getId());
        }
    }

}
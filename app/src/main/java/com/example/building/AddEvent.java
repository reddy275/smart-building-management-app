package com.example.building;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView addBlockHeading;
    private EditText eventNameET, eventDate, eventTime;
    private Spinner spinnerEventType;
    private Button addEventBtn;
    private DBHelper db;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Calendar calendar;

    public AddEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEvent newInstance(String param1, String param2) {
        AddEvent fragment = new AddEvent();
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
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        db = new DBHelper(requireActivity());
        // Initialize views
        addBlockHeading = view.findViewById(R.id.add_block_heading);
        eventNameET = view.findViewById(R.id.eventNameET);
        spinnerEventType = view.findViewById(R.id.spinnerEventType);
        eventDate = view.findViewById(R.id.eventDate);
        eventTime = view.findViewById(R.id.eventTime);
        addEventBtn = view.findViewById(R.id.addEventBtn);

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to add the event to the database
                addEvent();
            }
        });

        // Set up date picker
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        requireActivity(),
                        dateSetListener,
                        year, month, dayOfMonth
                ).show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minutes) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);
                updateTime();
            }
        };

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(
                        requireActivity(),
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                ).show();
            }
        });


        return view;
    }

    private void addEvent() {
        // Retrieve input values
        String eventName = eventNameET.getText().toString();
        String eventType = spinnerEventType.getSelectedItem().toString();
        String date = eventDate.getText().toString();
        String time = eventTime.getText().toString();

        // Validate input values if needed

        // Call the method in DBHelper to add the event
        long result = db.addEvent(eventName, eventType, date, time, 0); // Set numberOfPeople to 0 for now

        // Handle the result as needed
        if (result != -1) {
            Intent intent=new Intent(getContext(),SmartBuildAdminMenu.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(requireContext(), "Event added successfully", Toast.LENGTH_SHORT).show();
            // You may want to clear the input fields or navigate to another fragment/activity here
        } else {
            Toast.makeText(requireContext(), "Failed to add event", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDate() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        eventDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTime() {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        eventTime.setText(sdf.format(calendar.getTime()));
    }

}
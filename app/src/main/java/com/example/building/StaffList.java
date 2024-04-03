package com.example.building;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaffList extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DBHelper db;
    private StaffAdapter courseAdapter;
    ArrayList<StaffModel> blockCardModelArrayList = new ArrayList<StaffModel>();
    Button addBlockBtn;
    Button removeBlockBtn;
    private SearchView searchView;
    ArrayList<StaffModel> selected;
    public StaffList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaffList.
     */
    // TODO: Rename and change types and number of parameters
    public static StaffList newInstance(String param1, String param2) {
        StaffList fragment = new StaffList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff_list, container, false);
        RecyclerView cardsRV = view.findViewById(R.id.idRVStaff);
        db = new DBHelper(getActivity());


        LinearLayout nextBtn = view.findViewById(R.id.nextBtnLayout);
        Button assign = view.findViewById(R.id.assignBtn);
        nextBtn.setVisibility(View.GONE);


        Bundle args = getArguments();
        if (args != null) {
            if(args.getBoolean("assign")) {
                LinearLayout twoButtons = view.findViewById(R.id.twoButtons);
                twoButtons.setVisibility(View.GONE);
                nextBtn.setVisibility(View.VISIBLE);
            }
        }

        searchView = view.findViewById(R.id.searchViewStaff);

        // Initialize and set up your RecyclerView and Adapter...

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                courseAdapter.getFilter().filter(newText);
                return true;
            }
        });


        blockCardModelArrayList = (ArrayList<StaffModel>) db.getAllStaff();

        courseAdapter = new StaffAdapter(view.getContext(), blockCardModelArrayList);


        addBlockBtn = view.findViewById(R.id.addStaffBtn);
        removeBlockBtn = view.findViewById(R.id.removeStaffBtn);
        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);


        courseAdapter.setOnItemClickListener(new StaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StaffModel v) {
                AddStaff addBlockFragment = new AddStaff();

                Bundle bundle = new Bundle();
                bundle.putLong("staffID", v.getId());
                bundle.putString("block", db.getBlockNameById(Math.toIntExact(v.getBlockID())));
                bundle.putString("name", v.getName());
                bundle.putString("profession", v.getProfession());
                bundle.putString("joiningDate", v.getJoiningDate().toString());
                bundle.putString("username", v.getUsername());
                bundle.putString("phoneNumber", v.getPhoneNumber());
                // Add other details as needed

                addBlockFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addBlockFragment);
                fragmentTransaction.addToBackStack(null);  // Optional: Add fragment to back stack
                fragmentTransaction.commit();
            }
        });

        courseAdapter.setLong_listener(new StaffAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(String name) {
                Log.d("long press block", name);
            }
        });

        addBlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("Staff Fragment"));
                fragmentTransaction.replace(R.id.fragment_container, new AddStaff());
                fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
                fragmentTransaction.commit();
            }
        });

        ArrayList<StaffModel> finalBlockCardModelArrayList = blockCardModelArrayList;

        removeBlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = courseAdapter.getSelectedBlocks();
                if(selected.size() == 0) {
                    Toast.makeText(getActivity(), "Please select a block to remove", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i = 0; i < selected.size(); i++) {
                    int rowsAffected = db.deleteStaff(selected.get(i).getId());

                    if (rowsAffected > 0) {
                        finalBlockCardModelArrayList.remove(selected.get(i));
                        courseAdapter.notifyItemRemoved(finalBlockCardModelArrayList.indexOf(selected.get(i)));
                    } else {
                        Toast.makeText(getContext(), "Error: Couldn't delete "+ db.getBlockNameById(Math.toIntExact(selected.get(i).getBlockID())), Toast.LENGTH_LONG).show();
                    }

                }

                Toast.makeText(getContext(), "Successfully removed", Toast.LENGTH_LONG).show();
                courseAdapter.resetSelectedBlocks();
                courseAdapter.notifyDataSetChanged();
            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = courseAdapter.getSelectedBlocks();
                if(selected == null || selected.size() == 0) {
                    Toast.makeText(getActivity(), "Please select a staff member", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    selected = courseAdapter.getSelectedBlocks();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Job Assigned Succesfully!!");
                    alert.setMessage("Job Id: PL 2345");

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragment_container, new ShowIssueManager());
                            transaction.commit();
                        }
                    });

                    alert.show();
                }
            }
        });
        return view;
    }
}
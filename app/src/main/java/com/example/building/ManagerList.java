package com.example.building;

import android.os.Bundle;

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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHelper db;
    private ManagerAdapter courseAdapter;
    ArrayList<ManagerModel> blockCardModelArrayList = new ArrayList<ManagerModel>();
    Button addBlockBtn;
    Button removeBlockBtn;
    private SearchView searchView;

    public ManagerList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManagerList.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerList newInstance(String param1, String param2) {
        ManagerList fragment = new ManagerList();
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
        View view = inflater.inflate(R.layout.fragment_manager_list, container, false);
        RecyclerView cardsRV = view.findViewById(R.id.idRVManager);
        db = new DBHelper(getActivity());
        blockCardModelArrayList = (ArrayList<ManagerModel>) db.getAllManagers();

        courseAdapter = new ManagerAdapter(view.getContext(), blockCardModelArrayList);

        searchView = view.findViewById(R.id.searchViewManager);

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





        addBlockBtn = view.findViewById(R.id.addManagerBtn);
        removeBlockBtn = view.findViewById(R.id.removeManagerBtn);
        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);


        courseAdapter.setOnItemClickListener(new ManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ManagerModel v) {
                AddManager addBlockFragment = new AddManager();

                Bundle bundle = new Bundle();
                bundle.putLong("managerID", v.getManagerID());
                bundle.putString("block", db.getBlockNameById(v.getAssignedBlockID()));
                bundle.putString("name", v.getManagerName());
                bundle.putString("designation", "Manager");
                bundle.putString("joiningDate", v.getDateOfJoining().toString());
                bundle.putString("phoneNumber", v.getPhoneNumber());
                bundle.putString("nationality", v.getNationality());
                // Add other details as needed

                addBlockFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addBlockFragment);
                fragmentTransaction.addToBackStack(null);  // Optional: Add fragment to back stack
                fragmentTransaction.commit();
            }
        });

        courseAdapter.setLong_listener(new ManagerAdapter.OnLongClickListener() {
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
                fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("Manager Fragment"));
                fragmentTransaction.replace(R.id.fragment_container, new AddManager());
                fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
                fragmentTransaction.commit();
            }
        });

        ArrayList<ManagerModel> finalBlockCardModelArrayList = blockCardModelArrayList;
        removeBlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ManagerModel> selected = courseAdapter.getSelectedBlocks();
                if(selected.size() == 0) {
                    Toast.makeText(getActivity(), "Please select a block to remove", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i = 0; i < selected.size(); i++) {
                    int rowsAffected = db.deleteManager(selected.get(i).getManagerID());

                    if (rowsAffected > 0) {
                        finalBlockCardModelArrayList.remove(selected.get(i));
                        courseAdapter.notifyItemRemoved(finalBlockCardModelArrayList.indexOf(selected.get(i)));
                    } else {
                        Toast.makeText(getContext(), "Error: Couldn't delete "+ db.getBlockNameById(selected.get(i).getAssignedBlockID()), Toast.LENGTH_LONG).show();
                    }

                }

                Toast.makeText(getContext(), "Successfully removed", Toast.LENGTH_LONG).show();
                courseAdapter.resetSelectedBlocks();
                courseAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
package com.example.building;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class BlocksActivity extends Fragment {

    Button addBlockBtn;
    Button removeBlockBtn;
    
    private DBHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_blocks, container, false);
        RecyclerView cardsRV = view.findViewById(R.id.idRVCards);
        addBlockBtn = view.findViewById(R.id.addBlockBtn);
        removeBlockBtn = view.findViewById(R.id.removeBlockBtn);
        db = new DBHelper(getActivity());

        // Here, we have created new array list and added data to it
        ArrayList<BlockCardModel> blockCardModelArrayList = new ArrayList<BlockCardModel>();

        
        blockCardModelArrayList = (ArrayList<BlockCardModel>) db.getAllBlocks();

        // we are initializing our adapter class and passing our arraylist to it.
        BlockCardAdapter courseAdapter = new BlockCardAdapter(view.getContext(), blockCardModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        cardsRV.setLayoutManager(linearLayoutManager);
        cardsRV.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new BlockCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BlockCardModel v) {
                AddBlock addBlockFragment = new AddBlock();

                Bundle bundle = new Bundle();
                bundle.putLong("blockID", v.getId());
                bundle.putString("block", v.getBlock());
                bundle.putString("blockName", v.getBlockName());
                bundle.putBoolean("parkingAvailability", v.isParkingAvailability());
                bundle.putBoolean("furnished", v.isFurnished());
                // Add other details as needed

                addBlockFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addBlockFragment);
                fragmentTransaction.addToBackStack(null);  // Optional: Add fragment to back stack
                fragmentTransaction.commit();
            }
            });

        courseAdapter.setLong_listener(new BlockCardAdapter.OnLongClickListener() {
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
                    fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("Block Fragment"));
                    fragmentTransaction.replace(R.id.fragment_container, new AddBlock());
                fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
                fragmentTransaction.commit();
            }
        });

        ArrayList<BlockCardModel> finalBlockCardModelArrayList = blockCardModelArrayList;
        removeBlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BlockCardModel> selected = courseAdapter.getSelectedBlocks();
                if(selected.size() == 0) {
                    Toast.makeText(getActivity(), "Please select a block to remove", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i = 0; i < selected.size(); i++) {
                    int rowsAffected = db.deleteBlock(selected.get(i).getId());

                    if (rowsAffected > 0) {
                        finalBlockCardModelArrayList.remove(selected.get(i));
                        courseAdapter.notifyItemRemoved(finalBlockCardModelArrayList.indexOf(selected.get(i)));
                    } else {
                        Toast.makeText(getContext(), "Error: Couldn't delete "+ selected.get(i).getBlock(), Toast.LENGTH_LONG).show();
                    }

                }

                Toast.makeText(getContext(), "Successfully removed", Toast.LENGTH_LONG).show();
                courseAdapter.resetSelectedBlocks();
                courseAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_blocks);
//
//        RecyclerView cardsRV = findViewById(R.id.idRVCards);
//
//        // Here, we have created new array list and added data to it
//        ArrayList<BlockCardModel> blockCardModelArrayList = new ArrayList<BlockCardModel>();
//
//        blockCardModelArrayList.add(new BlockCardModel("A Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("B Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("C Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("D Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("E Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("F Block", R.drawable.apartment));
//
//        blockCardModelArrayList.add(new BlockCardModel("A Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("B Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("C Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("D Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("E Block", R.drawable.apartment));
//        blockCardModelArrayList.add(new BlockCardModel("F Block", R.drawable.apartment));
//
//
//        // we are initializing our adapter class and passing our arraylist to it.
//        BlockCardAdapter courseAdapter = new BlockCardAdapter(this, blockCardModelArrayList);
//
//        // below line is for setting a layout manager for our recycler view.
//        // here we are creating vertical list so we will provide orientation as vertical
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        // in below two lines we are setting layoutmanager and adapter to our recycler view.
//        cardsRV.setLayoutManager(linearLayoutManager);
//        cardsRV.setAdapter(courseAdapter);
//    }
}
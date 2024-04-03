package com.example.building;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class SectionsPagerAdapter extends FragmentStateAdapter {


    public SectionsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ShowIssueManager fragment = new ShowIssueManager();
        Bundle args = new Bundle();
        // Return the fragment based on the position
        if (position == 0) {
            args.putString("status", "Pending");
        } else if (position == 1) {
            args.putString("status", "Completed");
        }
        args.putString("from", "jobPage");
        // Pass arguments to the fragment
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
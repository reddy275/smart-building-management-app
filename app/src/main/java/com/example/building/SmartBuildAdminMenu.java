package com.example.building;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.building.databinding.ActivitySmartBuildAdminMenuBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SmartBuildAdminMenu extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_build_admin_menu);

        // Create a callback to handle back button press
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };

        // Add the callback to the activity
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);


        SharedPreferences preferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userDesignation = preferences.getString("designation", "User");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(userDesignation.equalsIgnoreCase("admin")) {
            navigationView.inflateMenu(R.menu.smart_build_admin_side_menu);
            navigationView.getMenu().getItem(0).setChecked(true);
            fragmentTransaction.add(R.id.fragment_container, new SmartBuildAdminHome2(), "main_fragment");
        } else if(userDesignation.equalsIgnoreCase("manager")) {
            navigationView.inflateMenu(R.menu.smart_build_manager_menu);
            navigationView.getMenu().getItem(0).setChecked(true);
            fragmentTransaction.add(R.id.fragment_container, new ShowIssueManager(), "main_fragment");
        } else {
            navigationView.inflateMenu(R.menu.smart_build_side_menu_user);
            navigationView.getMenu().getItem(0).setChecked(true);
            fragmentTransaction.add(R.id.fragment_container, new SmartBuildAdminHome2(), "main_fragment");
        }
//
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("clicked", "action");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.homeItem || id == R.id.userHome) {
                    Intent intent=new Intent(getApplicationContext(),SmartBuildAdminMenu.class);
                    startActivity(intent);
                    finish();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.profile || id == R.id.userProfile || id == R.id.managerProfile) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new MyProfile());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.staff) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new StaffList(), "Staff Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.manager) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ManagerList(), "Manager Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.events) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddEvent(), "Add Event Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.emergency_contacts) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddEmergencyContact(), "Add Contact Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if (id == R.id.announcements) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddAnnouncement(), "Add Announcement Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                    return true;
                } else if(id == R.id.userIssues) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ShowIssueUser(), "Manager Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                } else if(id == R.id.logout) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    // Navigate to the home page
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // Finish the current activity
                    finish();
                } else if(id == R.id.managerHome) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ShowIssueManager(), "Manager Home Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                } else if(id == R.id.userIssues) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ShowIssueUser(), "Manager Home Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                } else if(id == R.id.managerJobs) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new Jobs(), "Manager Jobs Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                } else if(id == R.id.complaints) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ShowIssueManager(), "Admin Complaints Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_smart_build_admin_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationView.getMenu().getItem(0).setChecked(false);
    }

}
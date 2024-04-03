package com.example.building;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ParkingArea extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_parking_area, container, false);


        TextView textView = view.findViewById(R.id.parkingAreaTV2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getString(R.string.parking_area_2), Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(HtmlCompat.fromHtml(getString(R.string.parking_area_2), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

        return view;
    }
}
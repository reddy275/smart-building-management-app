package com.example.building;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private Context context;
    private static ArrayList<EventModel> courseModelArrayList;
    public EventsAdapter(Context context, ArrayList<EventModel> courseModelArrayList) {
        this.context = context;
        EventsAdapter.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        EventModel model = courseModelArrayList.get(position);
        holder.eventType.setText(model.getEventType());

        holder.eventTime.setText(model.getTime());
        holder.eventCount.setText("Number of Members: " + (model.getNumberOfPeople().equalsIgnoreCase("0") ? "ALL": model.getNumberOfPeople()));

        Date date;
        String dateString = model.getDate();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception according to your requirements
            return;
        }

// Format the date to a string with the weekday and date
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd", Locale.getDefault());
        String formattedDate = sdf.format(date);

        holder.eventDay.setText(formattedDate.toUpperCase());

    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventType, eventTime, eventCount, eventDay;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventType = itemView.findViewById(R.id.eventType);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventCount = itemView.findViewById(R.id.eventMemberCount);
            eventDay = itemView.findViewById(R.id.dayDate);

            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}

package com.example.building;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<StaffModel> selectedBlocks;
    private static ArrayList<StaffModel> courseModelArrayList;
    private List<StaffModel> staffListFull;
    private DBHelper db;

    public StaffAdapter(Context context, ArrayList<StaffModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.selectedBlocks = new ArrayList<StaffModel>();
        this.db = new DBHelper(context);
    }
    @NonNull
    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_card, parent, false);
        return new StaffAdapter.ViewHolder(view);
    }


    public ArrayList<StaffModel> getSelectedBlocks() {
//        for (int i = 0; i < selectedBlocks.size(); i++) {
//            courseModelArrayList.remove(selectedBlocks.get(i));
//        }
        return selectedBlocks;
    }

    public void resetSelectedBlocks() {
        for (StaffModel model : courseModelArrayList) {
            model.setSelected(false);
        }
        selectedBlocks.clear();
        notifyDataSetChanged();
    }
    public void setLong_listener(StaffAdapter.OnLongClickListener longPressBlock) {
        long_listener = longPressBlock;
    }

    public void setStaffFilter(Filter staffFilter) {
        this.staffFilter = staffFilter;
    }

    private Filter staffFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StaffModel> filteredList = new ArrayList<>();

            if (staffListFull == null) {
                staffListFull = new ArrayList<>(courseModelArrayList);
            }


            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(staffListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (StaffModel staff : staffListFull) {
                    if (staff.getName().toLowerCase().contains(filterPattern) ||
                            staff.getProfession().toLowerCase().contains(filterPattern) ||
                            db.getBlockNameById(Math.toIntExact(staff.getBlockID())).toLowerCase().contains(filterPattern) ) {
                        filteredList.add(staff);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            courseModelArrayList.clear();
            courseModelArrayList.addAll((List<StaffModel>) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return staffFilter;
    }

    public interface OnItemClickListener {
        void onItemClick(StaffModel b);
    }

    public interface OnLongClickListener {
        void onLongClick(String name);
    }

    private StaffAdapter.OnItemClickListener listener;

    private StaffAdapter.OnLongClickListener long_listener;

    // Set the listener
    public void setOnItemClickListener(StaffAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.ViewHolder holder, int position) {
        StaffModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getName());

        holder.cardBlockTV.setText(db.getBlockNameById(Math.toIntExact(model.getBlockID())));
        holder.cardProfessionTV.setText(model.getProfession());

        switch(model.getProfession()) {
            case "Electrician": holder.cardIV.setImageResource(R.drawable.electrician);
            case "Plumber": holder.cardIV.setImageResource(R.drawable.plumber);
            case "Carpenter": holder.cardIV.setImageResource(R.drawable.carpenter);
            case "Gardener": holder.cardIV.setImageResource(R.drawable.agriculture);
            case "Painter": holder.cardIV.setImageResource(R.drawable.painter);
        }

        holder.setOnItemClickListener(listener);
        holder.setLongClickListener(long_listener);
        holder.resetViewState();

        holder.setOnItemClickListener(new StaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StaffModel block) {
                if (listener != null) {
                    listener.onItemClick(block);
                }
            }
        });


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                model.setSelected(!model.isSelected());
                holder.cardView.setCardBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                if(model.isSelected()) {
                    selectedBlocks.add(model);
                } else {
                    selectedBlocks.remove(model);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView cardIV;
        private final TextView cardNameTV;
        private final TextView cardProfessionTV;
        private final TextView cardBlockTV;
        private StaffAdapter.OnItemClickListener listener;
        private StaffAdapter.OnLongClickListener long_listener;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardIV = itemView.findViewById(R.id.staffIV);
            cardNameTV = itemView.findViewById(R.id.staffTVName);
            cardProfessionTV = itemView.findViewById(R.id.staffTVProfession);
            cardBlockTV = itemView.findViewById(R.id.staffTVBlock);

            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null) {
                // Pass the clicked StaffModel to the listener
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courseModelArrayList.get(position));
                }
            }
        }

        public void setOnItemClickListener(StaffAdapter.OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setLongClickListener(StaffAdapter.OnLongClickListener listener) {
            this.long_listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {

            if (long_listener != null) {

                long_listener.onLongClick(cardNameTV.getText().toString());
            }
            return true;
        }

        public void resetViewState() {
            cardView.setCardBackgroundColor(Color.WHITE);
        }
    }
}

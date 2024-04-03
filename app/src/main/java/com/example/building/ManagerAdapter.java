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

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ManagerModel> selectedBlocks;
    private static ArrayList<ManagerModel> courseModelArrayList;
    private List<ManagerModel> managerListFull;
    private DBHelper db;

    public ManagerAdapter(Context context, ArrayList<ManagerModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.selectedBlocks = new ArrayList<ManagerModel>();
    }

    @Override
    public Filter getFilter() {
        return staffFilter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_card, parent, false);
        return new ManagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db = new DBHelper(context);
        ManagerModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getManagerName());

        holder.cardBlockTV.setText(db.getBlockNameById(model.getAssignedBlockID()));
        holder.cardIV.setImageResource(R.drawable.manager);

        holder.setOnItemClickListener(listener);
        holder.setLongClickListener(long_listener);
        holder.resetViewState();

        holder.setOnItemClickListener(new ManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ManagerModel block) {
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

    public ArrayList<ManagerModel> getSelectedBlocks() {
//        for (int i = 0; i < selectedBlocks.size(); i++) {
//            courseModelArrayList.remove(selectedBlocks.get(i));
//        }
        return selectedBlocks;
    }

    public void resetSelectedBlocks() {
        for (ManagerModel model : courseModelArrayList) {
            model.setSelected(false);
        }
        selectedBlocks.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public void setLong_listener(ManagerAdapter.OnLongClickListener longPressBlock) {
        long_listener = longPressBlock;
    }

    public void setStaffFilter(Filter staffFilter) {
        this.staffFilter = staffFilter;
    }

    private Filter staffFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ManagerModel> filteredList = new ArrayList<>();

            if (managerListFull == null) {
                managerListFull = new ArrayList<>(courseModelArrayList);
            }


            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(managerListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();



                for (ManagerModel staff : managerListFull) {
                    String blockName = db.getBlockNameById(staff.getAssignedBlockID());
                    if (staff.getManagerName().toLowerCase().contains(filterPattern) ||
                            blockName.toLowerCase().contains(filterPattern) ) {
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
            courseModelArrayList.addAll((List<ManagerModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(ManagerModel b);
    }

    public interface OnLongClickListener {
        void onLongClick(String name);
    }

    private ManagerAdapter.OnItemClickListener listener;

    private ManagerAdapter.OnLongClickListener long_listener;

    // Set the listener
    public void setOnItemClickListener(ManagerAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView cardIV;
        private final TextView cardNameTV;
        private final TextView cardBlockTV;
        private ManagerAdapter.OnItemClickListener listener;
        private ManagerAdapter.OnLongClickListener long_listener;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardIV = itemView.findViewById(R.id.managerIV);
            cardNameTV = itemView.findViewById(R.id.managerTVName);
            cardBlockTV = itemView.findViewById(R.id.managerTVBlock);

            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courseModelArrayList.get(position));
                }
            }
        }

        public void setOnItemClickListener(ManagerAdapter.OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setLongClickListener(ManagerAdapter.OnLongClickListener listener) {
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

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

import java.util.ArrayList;
import java.util.List;

public class BlockCardAdapter extends RecyclerView.Adapter<BlockCardAdapter.ViewHolder>{

    private Context context;
    private ArrayList<BlockCardModel> selectedBlocks;
    private static ArrayList<BlockCardModel> courseModelArrayList;

    // Constructor
    public BlockCardAdapter(Context context, ArrayList<BlockCardModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.selectedBlocks = new ArrayList<BlockCardModel>();
    }

    public ArrayList<BlockCardModel> getSelectedBlocks() {
//        for (int i = 0; i < selectedBlocks.size(); i++) {
//            courseModelArrayList.remove(selectedBlocks.get(i));
//        }
        return selectedBlocks;
    }

    public void resetSelectedBlocks() {
        for (BlockCardModel model : courseModelArrayList) {
            model.setSelected(false);
        }
        selectedBlocks.clear();
        notifyDataSetChanged();
    }
    public void setLong_listener(BlockCardAdapter.OnLongClickListener longPressBlock) {
        long_listener = longPressBlock;
    }

    public interface OnItemClickListener {
        void onItemClick(BlockCardModel b);
    }

    public interface OnLongClickListener {
        void onLongClick(String name);
    }

    private BlockCardAdapter.OnItemClickListener listener;

    private BlockCardAdapter.OnLongClickListener long_listener;

    // Set the listener
    public void setOnItemClickListener(BlockCardAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlockCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new BlockCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        BlockCardModel model = courseModelArrayList.get(position);
        holder.cardNameTV.setText(model.getBlock());
        holder.cardIV.setImageResource(R.drawable.apartment);

        holder.setOnItemClickListener(listener);
        holder.setLongClickListener(long_listener);
        holder.resetViewState();

        holder.cardNameTV.setText(model.getBlock());
        holder.cardIV.setImageResource(R.drawable.apartment);

        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BlockCardModel block) {
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
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView cardIV;
        private final TextView cardNameTV;
        private BlockCardAdapter.OnItemClickListener listener;
        private BlockCardAdapter.OnLongClickListener long_listener;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardIV = itemView.findViewById(R.id.idIVCardImage);
            cardNameTV = itemView.findViewById(R.id.idTVCardName);

            final Context context = itemView.getContext();
            cardView = (CardView)itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null) {
                // Pass the clicked BlockCardModel to the listener
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(courseModelArrayList.get(position));
                }
            }
        }

        public void setOnItemClickListener(BlockCardAdapter.OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setLongClickListener(BlockCardAdapter.OnLongClickListener listener) {
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

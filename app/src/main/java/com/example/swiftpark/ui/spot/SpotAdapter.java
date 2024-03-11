package com.example.swiftpark.ui.spot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftpark.R;
import com.example.swiftpark.Spot;

import java.util.List;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.ViewHolder> {

    private List<Spot> spotList;
    private OnDeleteButtonClickListener deleteButtonClickListener;

    public SpotAdapter(List<Spot> spotList) {
        this.spotList = spotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spot spot = spotList.get(position);
        holder.textViewName.setText(spot.getName());
        holder.textViewAddress.setText(spot.getAddress());

        holder.deleteSpotButton.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteButtonClick(spot.getId()); // Pass ID
            }
        });
    }

    @Override
    public int getItemCount() {
        return spotList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAddress;
        Button deleteSpotButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            deleteSpotButton = itemView.findViewById(R.id.deleteSpotButton);
        }
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int spotId); // Change parameter to ID
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }
}

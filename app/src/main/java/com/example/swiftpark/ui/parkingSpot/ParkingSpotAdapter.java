package com.example.swiftpark.ui.parkingSpot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftpark.R;
import com.example.swiftpark.ui.home.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotAdapter extends RecyclerView.Adapter<ParkingSpotAdapter.ParkingSpotViewHolder> {

    private List<ParkingSpot> parkingSpots;

    public ParkingSpotAdapter() {
        this.parkingSpots = new ArrayList<>();
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots){
        this.parkingSpots= parkingSpots;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParkingSpotAdapter.ParkingSpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_lot_item, parent, false);
        return new ParkingSpotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingSpotAdapter.ParkingSpotViewHolder holder, int position) {
        ParkingSpot parkingSpot = parkingSpots.get(position);
        holder.bind(parkingSpot);

    }

    @Override
    public int getItemCount() {
        return parkingSpots.size();
    }

    public static class ParkingSpotViewHolder extends RecyclerView.ViewHolder {

        private TextView spotNameTextView;
        private TextView statusTextView;

        public ParkingSpotViewHolder(@NonNull View itemView) {
            super(itemView);
            spotNameTextView = itemView.findViewById(R.id.spotNameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }

        public void bind(ParkingSpot parkingSpot) {
            spotNameTextView.setText(parkingSpot.getSpotName());
            statusTextView.setText(parkingSpot.getStatus());
        }
    }

}
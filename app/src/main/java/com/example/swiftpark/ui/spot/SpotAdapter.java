package com.example.swiftpark.ui.spot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.swiftpark.R;
import com.example.swiftpark.ui.spot.Spot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.ViewHolder> {

    private List<Spot> spotList;
    private DatabaseReference mDatabase;

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
        if(spot != null) {


            holder.textViewName.setText(spot.getName());
            holder.textViewAddress.setText(spot.getAddress());
        }

        holder.deleteSpotButton.setOnClickListener(v -> {
            // deletes spot
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
}





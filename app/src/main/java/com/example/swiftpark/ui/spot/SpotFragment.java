package com.example.swiftpark.ui.spot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.swiftpark.R;

import com.example.swiftpark.ui.parkingSpot.ParkingLotActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpotFragment extends Fragment {
    private RecyclerView spotRecycler;
    private SpotAdapter spotAdapter;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    List<Spot> spotList;

    public SpotFragment() {
        // empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot, container, false);

        // Recycler setup
        spotRecycler = view.findViewById(R.id.spotRecycler);
        spotRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        spotList = new ArrayList<>();

        // Firebase init
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("spots").child(uid);

        spotAdapter = new SpotAdapter(spotList, new SpotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Spot spot) {
                openParkingLotActivity(spot);

            }
        });
        spotRecycler.setAdapter(spotAdapter);
        loadRecyclerView();

        // Opens dialog
        Button button = view.findViewById(R.id.addSpotButton);
        button.setOnClickListener(v -> {
            insertSpotDialog dialog = new insertSpotDialog(this);
            dialog.show(getParentFragmentManager(), "InsertSpot");
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadRecyclerView();
    }

    // Function that handles navigation when a favorite spot is selected
    private void openParkingLotActivity(Spot spot) {
        Intent intent = new Intent(getActivity(), ParkingLotActivity.class);
        intent.putExtra("selectedLot", spot.getLot());
        startActivity(intent);
    }

    // Function that will fetch all of the favorite spots and display them in a recycler view
    public void loadRecyclerView() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spotList.clear();
                for (DataSnapshot spotSnapshot : snapshot.getChildren()) {
                    Spot spot = spotSnapshot.getValue(Spot.class);
                    spotList.add(spot);
                }
                spotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    public void onDeleteClick(String spotKey) {
        databaseReference.child(spotKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Spot deleted successfully
                        // You may want to notify user or refresh UI
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }
}



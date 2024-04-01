package com.example.swiftpark.ui.parkingSpot;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.swiftpark.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView parkingSpotRecycler;
    private ParkingSpotAdapter spotAdapter;
    private Button returnButton;
    private TextView lotTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_parking_lot);

        // Init components
        returnButton = findViewById(R.id.returnButton);
        lotTextView = findViewById((R.id.lotTextView));
        parkingSpotRecycler = findViewById(R.id.parkingSpotRecycler);

        // Fetch Parking Lot
        String selectedLot = getIntent().getStringExtra("selectedLot");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("parking_lots").child(selectedLot);
        updateName();

        // Set Recycler View
        parkingSpotRecycler.setLayoutManager(new LinearLayoutManager(this));
        spotAdapter = new ParkingSpotAdapter();
        parkingSpotRecycler.setAdapter(spotAdapter);

        // Update the recycler view when there is an update to the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ParkingSpot> spots = new ArrayList<>();
                for(DataSnapshot spotSnapshot : snapshot.getChildren()) {
                    String spotName = spotSnapshot.getKey();
                    String status = spotSnapshot.child("status").getValue(String.class);
                    spots.add(new ParkingSpot(spotName, status));
                }
                spotAdapter.setParkingSpots(spots);
                spotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Returns to Spot fragment
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Handles title text view
    public void updateName() {
        String selectedLot = getIntent().getStringExtra("selectedLot");

        if(selectedLot.equals("Lot_A")){
            lotTextView.setText("Parking Lot A");
        }
        if(selectedLot.equals("Lot_B")){
            lotTextView.setText("Parking Lot B");
        }
        if(selectedLot.equals("Lot_C")){
            lotTextView.setText("Parking Lot C");
        }
    }
}

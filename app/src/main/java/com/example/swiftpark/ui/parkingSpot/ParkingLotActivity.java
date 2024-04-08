package com.example.swiftpark.ui.parkingSpot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView lotTextView, spotsFilledText;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_parking_lot);

        // Init components
        returnButton = findViewById(R.id.returnButton);
        lotTextView = findViewById((R.id.lotTextView));
        spotsFilledText = findViewById(R.id.spotsFilledText);
        parkingSpotRecycler = findViewById(R.id.parkingSpotRecycler);
        progressBar = findViewById(R.id.progressBar);

        // Fetch Parking Lot
        String selectedLot = getIntent().getStringExtra("selectedLot");
        String updatedLot = selectedLot.replace(" ", "_");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("parking_lots").child(updatedLot);
        updateName();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        parkingSpotRecycler.setLayoutManager(layoutManager);
        spotAdapter = new ParkingSpotAdapter();
        parkingSpotRecycler.setAdapter(spotAdapter);



        // Update the recycler view when there is an update to the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ParkingSpot> spots = new ArrayList<>();
                int totalSpots = 30;
                int availableSpots = 0;
                int spotsFilled = 0;
                for(DataSnapshot spotSnapshot : snapshot.getChildren()) {
                    String spotName = spotSnapshot.getKey();
                    String status = spotSnapshot.child("status").getValue(String.class);



                    if(status != null & status.equals("available")){
                        availableSpots++;
                        spotsFilled = 30 - availableSpots;
                        spotsFilledText.setText("Spots Filled: " + spotsFilled + "/30");
                        spots.add(new ParkingSpot(spotName, status));

                    }
                }

                int availablePercentage = (int) ((float) spotsFilled / totalSpots * 100);

                progressBar.setProgress(availablePercentage);

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

//        if(selectedLot.equals("Lot_A")){
//            lotTextView.setText("Parking Lot A");
//        }
//        if(selectedLot.equals("Lot_B")){
//            lotTextView.setText("Parking Lot B");
//        }
//        if(selectedLot.equals("Lot_C")){
//            lotTextView.setText("Parking Lot C");
//        }

        lotTextView.setText("Parking " + selectedLot.replace("_", " "));
    }
}

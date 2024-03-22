package com.example.swiftpark.ui.spot;

import android.annotation.SuppressLint;
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

    public SpotFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot, container, false);

        spotRecycler = view.findViewById(R.id.spotRecycler);
        spotRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        spotList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("spots").child(uid);


            spotAdapter = new SpotAdapter(spotList);
            spotRecycler.setAdapter(spotAdapter);
            loadRecyclerView();



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

    public void loadRecyclerView () {

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

    public static class SpotViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewAddress;

        public SpotViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
        }

        public void bind(Spot spot) {
            textViewName.setText(spot.getName());
            textViewAddress.setText(spot.getAddress());
        }
    }
}



package com.example.swiftpark.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class spotInfoDialog extends DialogFragment {
    private ReadAndWrite readAndWrite;
    private Fragment homeFragment;
    private Button exitButton;


    public spotInfoDialog(Fragment parent) { this.homeFragment = parent;};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.spot_info_dialog, container, false);
        TextView dialogName = root.findViewById(R.id.dialog_name);
        dialogName.setText("Parking Spot Information");
        TextView dialogAddress = root.findViewById(R.id.dialog_address);
        TextView dialogAvailabilities = root.findViewById(R.id.availabilities);
        exitButton = root.findViewById(R.id.cancelexit);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("parking_spot_available");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dialogAvailabilities.setText("Available: " + snapshot.getValue(Boolean.class).toString());
                }
                else{
                    dialogAvailabilities.setText("Availability: Could not fetch data!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("distance");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dialogAddress.setText("Distance: " + snapshot.getValue(Float.class).toString());
                }
                else{
                    dialogAddress.setText("Distance: Could not fetch data!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}

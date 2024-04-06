package com.example.swiftpark.ui.home;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Objects;

public class parkingInfoDialog extends DialogFragment {
    private ReadAndWrite readAndWrite;
    private Fragment homeFragment;
    private Activity parent;
    private String lotName;
    private Button exitButton;

    private TextView dialogAvailabilities;

    private ValueEventListener v = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            int total_Spots = 0;
            int available_Spots = 0;
            for (DataSnapshot spotS : snapshot.getChildren()) {
                try {
                    if (spotS.getKey().toLowerCase().contains("spot")) {
                        total_Spots += 1;
                        if (Objects.equals(spotS.child("status").getValue(String.class).toLowerCase(), "available")) {
                            available_Spots += 1;
                        }
                    }
                    dialogAvailabilities.setText("Available Spots: " + available_Spots + "/" + total_Spots);
                } catch (NullPointerException err) {
                    Log.d("SpotDialog", "Null Pointer Exception Raised");
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    public parkingInfoDialog(Fragment parent, String lot) {
        this.homeFragment = parent;
        this.lotName = lot;
    }

    public parkingInfoDialog(Activity parent, String lot) {
        this.parent = parent;
        this.lotName = lot;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.spot_info_dialog, container, false);
        TextView dialogName = root.findViewById(R.id.dialog_name);
        dialogName.setText(lotName.replace("_", " "));
        TextView dialogAddress = root.findViewById(R.id.dialog_address);
        dialogAddress.setText("");
        dialogAvailabilities= root.findViewById(R.id.availabilities);
        exitButton = root.findViewById(R.id.cancelexit);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("parking_lots").child(lotName);
        databaseReference.addListenerForSingleValueEvent(v);

        return root;
    }
}

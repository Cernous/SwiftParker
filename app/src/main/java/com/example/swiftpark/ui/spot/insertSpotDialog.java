package com.example.swiftpark.ui.spot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swiftpark.Database.ReadAndWrite;
//import com.example.swiftpark.SQLite.DatabaseHelper;
import com.example.swiftpark.R;
import com.example.swiftpark.ui.profile.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertSpotDialog extends DialogFragment {
    private ReadAndWrite readAndWrite;
    private SpotFragment spotFragment;

    public insertSpotDialog(SpotFragment spotFragment) {
        this.spotFragment = spotFragment;
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View root = inflater.inflate(R.layout.fragment_insert_spot_dialog, container, false);

            EditText nameEditText = root.findViewById(R.id.nameEditText);
            EditText addressEditText = root.findViewById(R.id.addressEditText);
            Button saveButton = root.findViewById(R.id.saveButton);
            Button cancelButton = root.findViewById(R.id.cancelButton);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        readAndWrite = new ReadAndWrite(databaseReference);

            saveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String name = nameEditText.getText().toString();
                    String address = addressEditText.getText().toString();

                    if (name.isEmpty() || address.isEmpty()) {
                        Toast.makeText(getActivity(), "Fields Cannot be Empty", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            readAndWrite.writeNewSpot(uid, name, address);
                            Toast.makeText(getActivity(), "Spot Saved !", Toast.LENGTH_LONG).show();
                            if (spotFragment != null) {
                                spotFragment.loadRecyclerView();
                            }
                            dismiss();
                        }
                    }
                }
                });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        return root;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
package com.example.swiftpark.ui.spot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertSpotDialog extends DialogFragment {
    private ReadAndWrite readAndWrite;
    private SpotFragment spotFragment;
    private Button saveButton, cancelButton;
    private EditText nameEditText;
    private Spinner lotSpinner;
    private TextView spinnerTextView;


    public insertSpotDialog(SpotFragment spotFragment) {
        this.spotFragment = spotFragment;
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View root = inflater.inflate(R.layout.fragment_insert_spot_dialog, container, false);

            nameEditText = root.findViewById(R.id.nameEditText);
            saveButton = root.findViewById(R.id.saveButton);
            cancelButton = root.findViewById(R.id.cancelButton);
            lotSpinner = root.findViewById(R.id.lotDropdown);

        // Dropdown menu set up
        String[] lotNames = {"Lot A", "Lot B", "Lot C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),  R.layout.spinner_item_custom, lotNames);
        lotSpinner.setAdapter(adapter);

        // Saves favorite Lot to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        readAndWrite = new ReadAndWrite(databaseReference);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nameEditText.getText().toString();
                    String lotSelected = lotSpinner.getSelectedItem().toString();

                    // Handles invalid entries
                    if (name.isEmpty() || lotSelected.isEmpty()) {
                        Toast.makeText(getActivity(), "Fields Cannot be Empty", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();

                            readAndWrite.writeNewSpot(uid, name, lotSelected);
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
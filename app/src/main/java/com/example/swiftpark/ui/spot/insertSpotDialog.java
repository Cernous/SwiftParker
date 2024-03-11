package com.example.swiftpark.ui.spot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swiftpark.DatabaseHelper;
import com.example.swiftpark.MainActivity;
import com.example.swiftpark.R;
import com.example.swiftpark.Spot;

public class insertSpotDialog extends DialogFragment {





    public insertSpotDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View root = inflater.inflate(R.layout.fragment_insert_spot_dialog, container, false);

            EditText nameEditText = root.findViewById(R.id.nameEditText);
            EditText addressEditText = root.findViewById(R.id.addressEditText);
            Button saveButton = root.findViewById(R.id.saveButton);
            Button cancelButton = root.findViewById(R.id.cancelButton);




            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nameEditText.getText().toString();
                    String address = addressEditText.getText().toString();

                    if (name.isEmpty() || address.isEmpty()) {
                        Toast.makeText(getActivity(), "Fields Cannot be Empty", Toast.LENGTH_LONG).show();
                    } else {
                        Spot spot = new Spot(name, address); // inserts new profile
                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                        databaseHelper.insertSpot(spot); // This also adds access entry, as defined in the DatabaseHelper class
                        Toast.makeText(getActivity(), "Spot Saved !", Toast.LENGTH_LONG).show();

                        dismiss();
                    }


                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });




        // Inflate the layout for this fragment
        return root;

    }

    @Override
    public void onStop() {
        super.onStop();

        SpotFragment spotFrag = new SpotFragment();
//        spotFrag.loadSpotRecycler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
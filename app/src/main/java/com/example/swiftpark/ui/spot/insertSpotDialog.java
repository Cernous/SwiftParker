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

import com.example.swiftpark.R;

public class insertSpotDialog extends DialogFragment {

    Button cancelButton, saveButton;


    public insertSpotDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_spot_dialog, container, false);

    }
}
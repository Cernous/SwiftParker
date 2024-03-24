package com.example.swiftpark.ui.spot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.swiftpark.R;

// Second DialogFragment class
public class SecondDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment_dialogue, container, false);
        // Set up your second dialog here
        return view;
    }
}

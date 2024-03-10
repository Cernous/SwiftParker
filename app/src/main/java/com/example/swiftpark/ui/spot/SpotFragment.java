package com.example.swiftpark.ui.spot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.swiftpark.R;
import com.example.swiftpark.databinding.FragmentSpotBinding;

public class SpotFragment extends Fragment {

    private FragmentSpotBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spot, container, false);

        // Find the Button by its ID
        Button button = view.findViewById(R.id.addSpotButton);

        // Set an OnClickListener to handle button clicks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to perform when the button is clicked
                // For example, you can navigate to another fragment or perform some other action
                Toast.makeText(getActivity(), "Button clicked", Toast.LENGTH_SHORT).show();


                        insertSpotDialog dialog = new insertSpotDialog();
                        dialog.show(getActivity().getSupportFragmentManager(), "InsertSpot");
             
            }
        });

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





//    DashboardViewModel dashboardViewModel =
//            new ViewModelProvider(this).get(DashboardViewModel.class);
//
//
//
//
//    binding = FragmentSpotBinding.inflate(inflater, container, false);
//    View root = binding.getRoot();
//
//
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
}
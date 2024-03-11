package com.example.swiftpark.ui.spot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.swiftpark.DatabaseHelper;
import com.example.swiftpark.R;
import com.example.swiftpark.Spot;
import com.example.swiftpark.databinding.FragmentSpotBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpotFragment extends Fragment {
    private RecyclerView spotRecycler;
    private SpotAdapter adapter;
    private List<Spot> spotList;
    private FragmentSpotBinding binding;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spot, container, false);
        spotRecycler = view.findViewById(R.id.spotRecycler);
        loadSpotRecycler();



        Button button = view.findViewById(R.id.addSpotButton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        insertSpotDialog dialog = new insertSpotDialog();
                        dialog.show(getActivity().getSupportFragmentManager(), "InsertSpot");
             
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        loadSpotRecycler();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void loadSpotRecycler(){
        spotRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        spotList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(getActivity());
        spotList = db.getAllSpots();
        adapter = new SpotAdapter(spotList);
        spotRecycler.setAdapter(adapter);

        adapter.setOnDeleteButtonClickListener(spotId -> {
            db.deleteSpot(spotId);
        });

    }


}
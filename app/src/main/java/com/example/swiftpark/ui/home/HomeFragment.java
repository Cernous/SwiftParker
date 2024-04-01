package com.example.swiftpark.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.R;

import com.example.swiftpark.ui.spot.Spot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "FragmentHomeBinding";
    private View view;

    //Demo part
    private Button demoButton;

    //Recycler
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private DatabaseReference databaseReference;
    private Button openParkingButton;

    private FirebaseAuth mAuth;

    private ArrayList<Spot> spotList = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("spots").child(uid);
        getNameText(uid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView = view.findViewById(R.id.suggestionRecycleView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getContext(), mNames, mImageUrls, mAddresses);
        recyclerView.setAdapter(adapter);
        initRecyclerView();



        //Demo Part
        demoButton = view.findViewById(R.id.demoButton);
        demoButton.setOnClickListener(v -> {
            spotInfoDialog dialog = new spotInfoDialog(this);
            dialog.show(getParentFragmentManager(), "spotInfoDialog");
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();

    }

    public void getNameText(String uid) {
        ReadAndWrite rw = new ReadAndWrite(databaseReference);
        rw.getProfile(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    TextView greetText = view.findViewById(R.id.greetText);
                    greetText.setText("Welcome, " + name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void loadSpotsList() {
        mNames.clear();
        mAddresses.clear();
        mImageUrls.clear();

        int size = spotList.size();
        if (size == 0) {
            mNames.add("No Favorites");
            mAddresses.add("To begin, start by adding spots to your favorites list.");
            mImageUrls.add("https://www.clipartbest.com/cliparts/dT8/XEd/dT8XEdj6c.png");
        } else {
            for (int i = 0; i < 6; i++) {
                if (i >= size) {
                    break;
                }
                mNames.add(spotList.get(i).getName());
                mAddresses.add(spotList.get(i).getLot());
                mImageUrls.add("https://media.istockphoto.com/vectors/private-parking-vector-id495111818?k=6&m=495111818&s=612x612&w=0&h=lutW_kHSeJJZiUZ7-Zj1S223oao17L3q3q8n2JtCAV4=");
            }
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview" + getActivity());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spotList.clear();
                int i = 0;
                for (DataSnapshot spotShot : snapshot.getChildren()) {
                    Spot spot = spotShot.getValue(Spot.class);
                    spotList.add(spot);
                }
                loadSpotsList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}
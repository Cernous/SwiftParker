package com.example.swiftpark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.swiftpark.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        createLots();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_spot, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    public void createLots() {
        // parking lots init
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference parkingLotsRef = database.getReference("parking_lots");

        parkingLotsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    parkingLotsRef.child("Demo").child("spot_1").child("status").setValue("available");
                    parkingLotsRef.child("Demo").child("spot_2").child("status").setValue("available");
                    parkingLotsRef.child("Demo").child("spot_3").child("status").setValue("available");

                    String[] lotNames = {"Lot_A","Lot_B", "Lot_C"};

                    for (String lotName : lotNames){
                        DatabaseReference lotRef = parkingLotsRef.child(lotName);

                        for(int i = 1; i <= 30; i++){
                            String spotName = "spot_" + i;

                            lotRef.child(spotName).child("status").setValue("available");
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
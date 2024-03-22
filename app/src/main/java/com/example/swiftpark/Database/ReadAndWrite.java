package com.example.swiftpark.Database;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import com.example.swiftpark.ui.profile.Profile;
import com.example.swiftpark.ui.spot.Spot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadAndWrite {

    private DatabaseReference mDatabase;

    public ReadAndWrite(DatabaseReference database) {
        mDatabase = FirebaseDatabase.getInstance().getReference(); // initialize database
    }

    public void writeNewUser(String userId, String name, String email) {
        Profile profile = new Profile(name, email);
        mDatabase.child("users").child(userId).setValue(profile);
    }

    public void writeNewSpot(String userId, String name, String address) {
        Spot spot = new Spot(name,address);
        mDatabase.child("spots").child(userId).push().setValue(spot);
    }

    public void deleteSpot(String userId, String spotId){
        mDatabase.child("spots").child(userId).child(spotId).removeValue();

    }


}

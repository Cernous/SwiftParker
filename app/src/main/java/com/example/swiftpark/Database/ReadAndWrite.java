package com.example.swiftpark.Database;

import com.example.swiftpark.ui.profile.Profile;
import com.example.swiftpark.ui.spot.Spot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadAndWrite {

    private DatabaseReference mDatabase;

    public ReadAndWrite(DatabaseReference database) {
        mDatabase = FirebaseDatabase.getInstance().getReference(); // initialize database
    }

    public void writeNewProfile(String userId, String fullName, String email) {

        DatabaseReference profilesRef = FirebaseDatabase.getInstance().getReference().child("profiles").child(userId);

        profilesRef.child("name").setValue(fullName);
        profilesRef.child("email").setValue(email);
    }

    public void getProfile(String userId, ValueEventListener listener){
        DatabaseReference profilesRef = FirebaseDatabase.getInstance().getReference().child("profiles").child(userId);
        profilesRef.addListenerForSingleValueEvent(listener);
    }


    public void writeNewSpot(String userId, String name, String lot) {
        Spot spot = new Spot(name,lot);
        mDatabase.child("spots").child(userId).push().setValue(spot);
    }

    public void deleteSpot(String userId, String spotId){
        mDatabase.child("spots").child(userId).child(spotId).removeValue();

    }




}

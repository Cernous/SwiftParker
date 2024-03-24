package com.example.swiftpark.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.LoginSignUpActivity.LoginActivity;
import com.example.swiftpark.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private EditText profileNameEditText, emailEditText;
    private ImageView profilePicture;
    private Button changePasswordButton, logoutButton, editProfileButton, settingsButton;
    private TextView profileNameTextView;

    FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;
    ReadAndWrite readAndWrite;

    Profile profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        profileNameTextView = view.findViewById(R.id.profileNameTextView);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        editProfileButton = view.findViewById(R.id.editProfileButton);
        settingsButton = view.findViewById(R.id.settingsButton);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);

        logoutButton = view.findViewById(R.id.loginButton);

        firebaseDatabase = FirebaseDatabase.getInstance();

        profile = new Profile();

        readAndWrite = new ReadAndWrite(databaseReference);



        readAndWrite.getProfile(uid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("name").getValue(String.class);

                    profileNameTextView.setText(fullName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logout Succesful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                readAndWrite.getProfile(uid, new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String fullName = snapshot.child("name").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);

                            profileNameTextView.setText(fullName);

                            if (email != null) {
                                auth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Sent email", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                }
                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error){

                        }

                });



            }
        });



//        saveProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = profileNameEditText.getText().toString();
//                String email = emailEditText.getText().toString();
//                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email)) {
//                    Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
//                } else {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    if (user != null) {
//                        String uid = user.getUid();
//                        readAndWrite.writeNewUser(uid, name, email);
//                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
//                      }
//                }
//            }
//
//        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
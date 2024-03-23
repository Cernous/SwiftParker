package com.example.swiftpark.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.LoginSignUpActivity.LoginActivity;
import com.example.swiftpark.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    private EditText profileNameEditText, emailEditText;
    private Button saveProfileButton, logoutButton, editProfileButton;

    FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;
    ReadAndWrite readAndWrite;

    Profile profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        editProfileButton = view.findViewById(R.id.editProfileButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        firebaseDatabase = FirebaseDatabase.getInstance();

        profile = new Profile();

        readAndWrite = new ReadAndWrite(databaseReference);



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logout Succesful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = profileNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid();
                        readAndWrite.writeNewUser(uid, name, email);
                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                      }
                }
            }

        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
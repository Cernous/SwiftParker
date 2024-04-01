package com.example.swiftpark.ui.profile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swiftpark.Database.ReadAndWrite;
import com.example.swiftpark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editProfileFragment extends DialogFragment {
    private ProfileFragment profileFragment;
    private Button cancelButton, saveProfileButton;
    private EditText editNameEdit, editEmailEdit;
    private DatabaseReference mDatabase;

    public editProfileFragment(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Init components
        saveProfileButton = root.findViewById(R.id.deleteButton);
        cancelButton = root.findViewById(R.id.cancelButton);
        editNameEdit = root.findViewById(R.id.editNameEdit);
        editEmailEdit = root.findViewById(R.id.editEmailEdit);

        // Firebase init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = mDatabase.child("profiles").child(uid);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    editNameEdit.setText(name);
                    editEmailEdit.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Invokes ReadAndWrite class to write new information to the database when the save button is pressed
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference profileRef = database.getReference();
                ReadAndWrite readAndWrite = new ReadAndWrite(profileRef);

                String fullName, email;
                fullName = editNameEdit.getText().toString().trim();
                email = editEmailEdit.getText().toString().trim();

                // Error handling when fields are incorrect
                if (fullName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getActivity(), "Fields Cannot be Empty", Toast.LENGTH_LONG).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.verifyBeforeUpdateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Email is verified
                                }
                            }
                        });

                        // If the user is valid, will overwrite information in the database
                        String uid = user.getUid();
                        readAndWrite.writeNewProfile(uid, fullName, email);
                        Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_LONG).show();
                        profileFragment.refreshNameText();
                        dismiss();
                    }
                    }
            }
        });

        // Closes dialog fragment
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return root;
    }
}
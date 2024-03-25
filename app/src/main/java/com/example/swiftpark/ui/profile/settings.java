package com.example.swiftpark.ui.profile;

import android.content.Intent;
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
import com.example.swiftpark.LoginSignUpActivity.LoginActivity;
import com.example.swiftpark.LoginSignUpActivity.RegisterActivity;
import com.example.swiftpark.MainActivity;
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

public class settings extends DialogFragment {
    private ProfileFragment profileFragment;
    private Button cancelButton, deleteButton;
    private EditText editNameEdit, editEmailEdit;
    private DatabaseReference mDatabase;

    public settings(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        deleteButton = root.findViewById(R.id.deleteButton);
        cancelButton = root.findViewById(R.id.cancelButton);


        mDatabase = FirebaseDatabase.getInstance().getReference();



        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                   // yay !
                                }
                            }
                        });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return root;
    }
}
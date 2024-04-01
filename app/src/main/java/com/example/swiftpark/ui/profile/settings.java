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

import com.example.swiftpark.LoginSignUpActivity.LoginActivity;
import com.example.swiftpark.R;
import com.example.swiftpark.ui.home.spotInfoDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class settings extends DialogFragment {
    private ProfileFragment profileFragment;
    private Button cancelButton, deleteButton, demoButton;
    private DatabaseReference mDatabase;

    public settings(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        // Init Components
        deleteButton = root.findViewById(R.id.deleteButton);
        cancelButton = root.findViewById(R.id.cancelButton);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Opens Demo Dialog
        demoButton = root.findViewById(R.id.demoButton);
        demoButton.setOnClickListener(v -> {
            spotInfoDialog dialog = new spotInfoDialog(this);
            dialog.show(getParentFragmentManager(), "spotInfoDialog");
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
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
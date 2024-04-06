package com.example.swiftpark.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_alert, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setView(dialogView);
                builder.setTitle("Delete Profile")
                        .setMessage("Are you sure you want to delete your profile? This action cannot be undone");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    String uid = user.getUid();
                                    FirebaseDatabase.getInstance().getReference("profiles").child(uid).removeValue();
                                    FirebaseDatabase.getInstance().getReference("spots").child(uid).removeValue();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });

                builder.setNegativeButton(android.R.string.no, null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
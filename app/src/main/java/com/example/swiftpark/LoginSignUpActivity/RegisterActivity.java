package com.example.swiftpark.LoginSignUpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swiftpark.MainActivity;
import com.example.swiftpark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText register_email, fullnameEditText, register_password;
    Button create_account_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        register_email = findViewById(R.id.register_email);
        fullnameEditText = findViewById(R.id.fullnameEditText);
        register_password = findViewById(R.id.register_password);
        create_account_button = findViewById(R.id.create_account_button);

        mAuth = FirebaseAuth.getInstance();

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

    private void registerNewUser() {
        String name, email, password;
        name = fullnameEditText.getText().toString();
        email = register_email.getText().toString();
        password = register_password.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),"Please enter a valid name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Please enter a valid email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Please enter a valid password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });


    }



}
package com.example.swiftpark;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText login_email, login_password;
    Button login_button, register_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

            login_email = findViewById(R.id.login_email);
            login_password = findViewById(R.id.login_password);
            login_button = findViewById(R.id.login_button);
            register_button = findViewById(R.id.register_button);


            register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUserAccount();
                }
            });
        }

        private void loginUserAccount() {
        String email, password;
        email = login_email.getText().toString();
        password = login_password.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(),
                                "Please enter a valid email",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(),
                                "Please enter a valid password",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                        "Login successful!",
                                        Toast.LENGTH_LONG)
                                .show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                        "Login failed",
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }



    }

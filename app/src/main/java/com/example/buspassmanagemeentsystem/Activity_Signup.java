package com.example.buspassmanagemeentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Activity_Signup extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.fullName);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        Button signupButton = findViewById(R.id.btnSignUp);
        TextView loginText = findViewById(R.id.loginText);

        signupButton.setOnClickListener(v -> {

        });


        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Signup.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
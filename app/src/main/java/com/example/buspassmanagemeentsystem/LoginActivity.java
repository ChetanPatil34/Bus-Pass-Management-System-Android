package com.example.buspassmanagemeentsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        Button loginButton = findViewById(R.id.btnLogin);
        TextView tvSignup = findViewById(R.id.tvSignup); // 🔹 Add this line

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, Dashboard.class));
                            finish();
                        } else {
                            String errorMessage = (task.getException() != null) ? task.getException().getMessage() : "Unknown error occurred";
                            Toast.makeText(this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // 🔹 Signup text click listener
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Activity_Signup.class);
            startActivity(intent);
        });
    }
}
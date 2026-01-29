package com.example.buspassmanagemeentsystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class ApplyNewPassActivity extends AppCompatActivity {

    EditText etName, etCollege, etFrom, etTo, etRouteNo;
    Spinner spinnerDuration;
    Button btnSubmit;

    FirebaseFirestore db;
    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_new_pass);

        // Bind views
        etName = findViewById(R.id.etName);
        etCollege = findViewById(R.id.etCollege);
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        etRouteNo = findViewById(R.id.etRouteNo);
        spinnerDuration = findViewById(R.id.spinnerDuration);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnSubmit.setOnClickListener(v -> {
            if (validateFields()) {
                progressDialog.setMessage("Submitting...");
                progressDialog.show();
                saveDataToFirestore();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateFields() {
        return !etName.getText().toString().trim().isEmpty()
                && !etCollege.getText().toString().trim().isEmpty()
                && !etFrom.getText().toString().trim().isEmpty()
                && !etTo.getText().toString().trim().isEmpty()
                && !etRouteNo.getText().toString().trim().isEmpty();
    }

    private void saveDataToFirestore() {

        if (auth.getCurrentUser() == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        String passId = UUID.randomUUID().toString();

        HashMap<String, Object> pass = new HashMap<>();
        pass.put("passId", passId);
        pass.put("name", etName.getText().toString());
        pass.put("college", etCollege.getText().toString());
        pass.put("from", etFrom.getText().toString());
        pass.put("to", etTo.getText().toString());
        pass.put("routeNo", etRouteNo.getText().toString());
        pass.put("duration", spinnerDuration.getSelectedItem().toString());
        pass.put("status", "Pending");

        db.collection("users")
                .document(userId)
                .collection("passes")
                .document(passId)
                .set(pass)
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Pass Applied Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Log.e("FIRESTORE", e.getMessage());
                });
    }
}

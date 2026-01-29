package com.example.buspassmanagemeentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    LinearLayout llViewPasses, llApplyNew, llRenew, llProfile, llSettings, llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        llViewPasses = findViewById(R.id.llViewPasses);
        llApplyNew = findViewById(R.id.llApplyNew);
        llRenew = findViewById(R.id.llRenew);
        llProfile = findViewById(R.id.llProfile);
        llSettings = findViewById(R.id.llSettings);
        llLogout = findViewById(R.id.llLogout);

        // Set onClickListeners
        llViewPasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "View Passes Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this, ViewPasses.class));
            }
        });

        llApplyNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Apply New Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this, ApplyNewPassActivity.class));
            }
        });

        llRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Renew Pass Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this,RenewPassActivity.class));
            }
        });

        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(Dashboard.this, ProfileActivity.class));
            }
        });

        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(Dashboard.this, SettingsActivity.class));
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Logged Out", Toast.LENGTH_SHORT).show();
                // Optional: Handle logout logic here
                // finish();
            }
        });
    }
}

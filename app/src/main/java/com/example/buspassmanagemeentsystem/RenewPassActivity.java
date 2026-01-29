package com.example.buspassmanageementsystem;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RenewPassActivity extends AppCompatActivity {

    EditText etName;
    Spinner spinnerDuration;
    Button btnRenew;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_pass);

        // ✅ FIND VIEWS
        etName = findViewById(R.id.etName);
        spinnerDuration = findViewById(R.id.spinnerDuration);
        btnRenew = findViewById(R.id.btnRenew);

        dbHelper = new DatabaseHelper(this);

        // ✅ Spinner data
        String[] durationList = {"1 Month", "3 Months", "6 Months"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                durationList
        );
        spinnerDuration.setAdapter(adapter);

        btnRenew.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String duration = spinnerDuration.getSelectedItem().toString();

            if (name.isEmpty()) {
                Toast.makeText(this,
                        "Please enter name",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            String newExpiry = calculateExpiry(duration);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("expiry", newExpiry);

            // ✅ NAME BASED UPDATE
            int updated = db.update(
                    "buspass",
                    cv,
                    "name=?",
                    new String[]{name}
            );

            if (updated > 0) {
                Toast.makeText(this,
                        "Pass Renewed Successfully",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this,
                        "No Pass Found for this Name",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String calculateExpiry(String duration) {
        if (duration.equals("1 Month")) return "2026-02-01";
        if (duration.equals("3 Months")) return "2026-04-01";
        if (duration.equals("6 Months")) return "2026-07-01";
        return "2026-01-01";
    }
}

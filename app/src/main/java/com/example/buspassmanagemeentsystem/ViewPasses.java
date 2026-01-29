package com.example.buspassmanagemeentsystem;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewPasses extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseFirestore db;

    List<QueryDocumentSnapshot> passList;
    PassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_passes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        passList = new ArrayList<>();
        adapter = new PassAdapter();
        recyclerView.setAdapter(adapter);

        loadPasses();
    }

    private void loadPasses() {

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("passes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    passList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        passList.add(doc);
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    // ================= ADAPTER INSIDE SAME FILE =================
    class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassViewHolder> {

        @NonNull
        @Override
        public PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new PassViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PassViewHolder holder, int position) {

            QueryDocumentSnapshot doc = passList.get(position);

            String from = doc.getString("from");
            String to = doc.getString("to");
            String status = doc.getString("status");

            holder.text1.setText("Route: " + from + " → " + to);
            holder.text2.setText("Status: " + status);

            if ("Approved".equals(status))
                holder.text2.setTextColor(Color.GREEN);
            else if ("Rejected".equals(status))
                holder.text2.setTextColor(Color.RED);
            else
                holder.text2.setTextColor(Color.parseColor("#FFA500"));
        }

        @Override
        public int getItemCount() {
            return passList.size();
        }

        class PassViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            public PassViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}

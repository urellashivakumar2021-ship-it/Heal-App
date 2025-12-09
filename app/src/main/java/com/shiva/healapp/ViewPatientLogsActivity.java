package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPatientLogsActivity extends AppCompatActivity {

    RecyclerView rvLogs;
    ArrayList<UserLogModel> logs = new ArrayList<>();
    DBHelper db;
    int userId;
    UserLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_logs); // reused layout

        rvLogs = findViewById(R.id.rvUserLogs);
        db = new DBHelper(this);

        // Doctor selects patient — here we expect user_id passed by intent
        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Patient not specified.", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter = new UserLogAdapter(this, logs);
        rvLogs.setLayoutManager(new LinearLayoutManager(this));
        rvLogs.setAdapter(adapter);

        // Setup "Give Advice" button (visible for doctor)
        Button btnGiveAdvice = findViewById(R.id.btnGiveAdvice);
        if (btnGiveAdvice != null) {
            btnGiveAdvice.setOnClickListener(v -> {
                Intent i = new Intent(this, GiveAdviceActivity.class);
                i.putExtra("user_id", userId);
                startActivity(i);
            });
        }

        loadLogs(userId);
    }

    private void loadLogs(int userId) {
        logs.clear();
        Cursor c = null;
        try {
            c = db.getUserLogs(userId);
            if (c != null && c.moveToFirst()) {
                do {
                    logs.add(new UserLogModel(
                            c.getString(c.getColumnIndexOrThrow("timestamp")),
                            c.getInt(c.getColumnIndexOrThrow("sleep")),
                            c.getDouble(c.getColumnIndexOrThrow("water")),
                            c.getInt(c.getColumnIndexOrThrow("stress")),
                            c.getString(c.getColumnIndexOrThrow("diet")),
                            c.getString(c.getColumnIndexOrThrow("activity")),
                            c.getString(c.getColumnIndexOrThrow("mood")),
                            c.getInt(c.getColumnIndexOrThrow("heartrate"))
                    ));
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) c.close();
        }

        adapter.notifyDataSetChanged();
    }
}

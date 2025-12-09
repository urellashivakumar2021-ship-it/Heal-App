package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewUserLogsActivity extends AppCompatActivity {

    RecyclerView rvUserLogs;
    DBHelper db;
    ArrayList<UserLogModel> logList = new ArrayList<>();
    UserLogAdapter adapter;
    int userId;
    Button btnGiveAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_logs);

        rvUserLogs = findViewById(R.id.rvUserLogs);
        btnGiveAdvice = findViewById(R.id.btnGiveAdvice); // present in layout but for admin we hide it
        db = new DBHelper(this);

        // For admin/others, user_id should come via intent
        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not specified.", Toast.LENGTH_SHORT).show();
            // hide advice button for non-doctor context
            if (btnGiveAdvice != null) btnGiveAdvice.setVisibility(View.GONE);
            // still attempt to load nothing
        } else {
            // hide advice button because this screen is admin/general view
            if (btnGiveAdvice != null) btnGiveAdvice.setVisibility(View.GONE);
        }

        adapter = new UserLogAdapter(this, logList);
        rvUserLogs.setLayoutManager(new LinearLayoutManager(this));
        rvUserLogs.setAdapter(adapter);

        if (userId != -1) loadLogs(userId);
    }

    private void loadLogs(int userId) {
        logList.clear();
        Cursor c = null;
        try {
            c = db.getUserLogs(userId);
            if (c != null && c.moveToFirst()) {
                do {
                    String timestamp = c.getString(c.getColumnIndexOrThrow("timestamp"));
                    int sleep = c.getInt(c.getColumnIndexOrThrow("sleep"));
                    double water = c.getDouble(c.getColumnIndexOrThrow("water"));
                    int stress = c.getInt(c.getColumnIndexOrThrow("stress"));
                    String diet = c.getString(c.getColumnIndexOrThrow("diet"));
                    String activity = c.getString(c.getColumnIndexOrThrow("activity"));
                    String mood = c.getString(c.getColumnIndexOrThrow("mood"));
                    int heart = c.getInt(c.getColumnIndexOrThrow("heartrate"));

                    logList.add(new UserLogModel(timestamp, sleep, water, stress, diet, activity, mood, heart));
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) c.close();
        }

        adapter.notifyDataSetChanged();
    }
}

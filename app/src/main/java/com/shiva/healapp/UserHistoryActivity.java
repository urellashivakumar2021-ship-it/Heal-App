package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class UserHistoryActivity extends AppCompatActivity {

    RecyclerView rvUserHistory;
    ArrayList<UserLogModel> logList = new ArrayList<>();
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        rvUserHistory = findViewById(R.id.rvUserHistory);
        db = new DBHelper(this);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        loadLogs();

        rvUserHistory.setLayoutManager(new LinearLayoutManager(this));
        rvUserHistory.setAdapter(new UserLogAdapter(this, logList));
    }

    private void loadLogs() {
        logList.clear();
        Cursor c = db.getUserLogs(userId);

        while (c.moveToNext()) {
            logList.add(new UserLogModel(
                    c.getString(c.getColumnIndexOrThrow("timestamp")),
                    c.getInt(c.getColumnIndexOrThrow("sleep")),
                    c.getDouble(c.getColumnIndexOrThrow("water")),
                    c.getInt(c.getColumnIndexOrThrow("stress")),
                    c.getString(c.getColumnIndexOrThrow("diet")),
                    c.getString(c.getColumnIndexOrThrow("activity")),
                    c.getString(c.getColumnIndexOrThrow("mood")),
                    c.getInt(c.getColumnIndexOrThrow("heartrate"))
            ));
        }
    }
}

package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewPatientsActivity extends AppCompatActivity {

    RecyclerView rvPatients;
    ArrayList<PatientModel> list = new ArrayList<>();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        rvPatients = findViewById(R.id.rvPatients);
        db = new DBHelper(this);

        loadPatients();

        rvPatients.setLayoutManager(new LinearLayoutManager(this));
        rvPatients.setAdapter(new PatientAdapter(this, list));
    }

    private void loadPatients() {
        list.clear();

        Cursor users = db.getAllUsers();

        while (users.moveToNext()) {
            int id = users.getInt(users.getColumnIndexOrThrow("id"));
            String name = users.getString(users.getColumnIndexOrThrow("name"));
            String email = users.getString(users.getColumnIndexOrThrow("email"));

            // Get last log timestamp
            Cursor logs = db.getReadableDatabase().rawQuery(
                    "SELECT timestamp FROM health_logs WHERE user_id=? ORDER BY timestamp DESC LIMIT 1",
                    new String[]{String.valueOf(id)}
            );

            String lastLog = null;
            if (logs.moveToFirst()) {
                lastLog = logs.getString(0);
            }

            list.add(new PatientModel(id, name, email, lastLog));
        }
    }
}

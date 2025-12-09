package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class ManageDoctorsActivity extends AppCompatActivity {

    RecyclerView rvDoctors;
    ArrayList<DoctorModel> doctorList = new ArrayList<>();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        rvDoctors = findViewById(R.id.rvDoctors);
        db = new DBHelper(this);

        loadDoctors();

        rvDoctors.setLayoutManager(new LinearLayoutManager(this));
        rvDoctors.setAdapter(new DoctorAdapter(this, doctorList));
    }

    private void loadDoctors() {
        doctorList.clear();
        Cursor c = db.getAllDoctors();

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String email = c.getString(c.getColumnIndexOrThrow("email"));
            String spec = c.getString(c.getColumnIndexOrThrow("specialization"));
            String phone = c.getString(c.getColumnIndexOrThrow("phone"));

            doctorList.add(new DoctorModel(id, name, email, spec, phone));
        }
    }
}

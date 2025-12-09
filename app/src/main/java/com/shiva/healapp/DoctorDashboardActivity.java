package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class DoctorDashboardActivity extends AppCompatActivity {

    Button btnViewPatients, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        btnViewPatients = findViewById(R.id.btnViewPatients);
        btnLogout = findViewById(R.id.btnDocLogout);

        // Open patient list
        btnViewPatients.setOnClickListener(v ->
                startActivity(new Intent(this, ViewPatientsActivity.class))
        );

        // Logout doctor
        btnLogout.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("doctor_session", MODE_PRIVATE);
            sp.edit().clear().apply();
            finish();
        });
    }
}

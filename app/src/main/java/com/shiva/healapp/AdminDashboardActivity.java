package com.shiva.healapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnManageDoctors, btnViewUsers, btnAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnManageDoctors = findViewById(R.id.btnManageDoctors);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);

        btnManageDoctors.setOnClickListener(v ->
                startActivity(new Intent(this, ManageDoctorsActivity.class))
        );

        btnViewUsers.setOnClickListener(v ->
                startActivity(new Intent(this, ViewUsersActivity.class))
        );

        btnAdminLogout.setOnClickListener(v -> finish());
    }
}

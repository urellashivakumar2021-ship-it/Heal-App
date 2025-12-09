package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorLoginActivity extends AppCompatActivity {

    EditText etDocLoginEmail, etDocLoginPassword;
    Button btnDocLogin, btnDocGoRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        db = new DBHelper(this);

        etDocLoginEmail = findViewById(R.id.etDocLoginEmail);
        etDocLoginPassword = findViewById(R.id.etDocLoginPassword);
        btnDocLogin = findViewById(R.id.btnDocLogin);
        btnDocGoRegister = findViewById(R.id.btnDocGoRegister);

        btnDocLogin.setOnClickListener(v -> {
            String email = etDocLoginEmail.getText().toString().trim();
            String pass = etDocLoginPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor c = db.findDoctorByEmailPassword(email, pass);

            if (c != null && c.moveToFirst()) {
                int doctorId = c.getInt(c.getColumnIndexOrThrow("id"));
                SharedPreferences sp = getSharedPreferences("doctor_session", MODE_PRIVATE);
                sp.edit().putInt("doctor_id", doctorId).apply();

                startActivity(new Intent(this, DoctorDashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        btnDocGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, DoctorRegisterActivity.class))
        );
    }
}

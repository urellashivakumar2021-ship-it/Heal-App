package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etAdminEmail, etAdminPassword;
    Button btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPassword = findViewById(R.id.etAdminPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(v -> {
            String email = etAdminEmail.getText().toString().trim();
            String pass = etAdminPassword.getText().toString().trim();

            if (email.equals("admin@gmail.com") && pass.equals("admin123")) {

                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();

                // FIX: redirect to admin dashboard
                Intent i = new Intent(this, AdminDashboardActivity.class);
                startActivity(i);

                finish();

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        db = new DBHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor c = null;
            try {
                c = db.findUserByEmailPassword(email, password);

                if (c != null && c.moveToFirst()) {

                    // Safely fetch ID from column "id"
                    int userId = c.getInt(c.getColumnIndexOrThrow("id"));

                    // Save user session
                    SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
                    sp.edit().putInt("user_id", userId).apply();

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, UserDashboardActivity.class));
                    finish();

                } else {
                    Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Login failed. Please reinstall app.", Toast.LENGTH_LONG).show();
            } finally {
                if (c != null) c.close();
            }
        });

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}

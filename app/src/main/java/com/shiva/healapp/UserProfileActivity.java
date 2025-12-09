package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    EditText etName, etPassword;
    Button btnSave;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);
        if (userId == -1) {
            finish();
            return;
        }

        db = new DBHelper(this);

        etName = findViewById(R.id.etProfileName);
        etPassword = findViewById(R.id.etProfilePassword);
        btnSave = findViewById(R.id.btnSaveProfile);

        loadUserDetails();

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String pwd = etPassword.getText().toString().trim();

            if (name.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = db.updateUser(userId, name, pwd);
            Toast.makeText(this, result > 0 ? "Updated!" : "Update failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserDetails() {
        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM users WHERE id=?",
                new String[]{String.valueOf(userId)}
        );

        if (c.moveToFirst()) {
            etName.setText(c.getString(c.getColumnIndexOrThrow("name")));
            etPassword.setText(c.getString(c.getColumnIndexOrThrow("password")));
        }

        c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        if (sp.getInt("user_id", -1) == -1) finish();
    }
}

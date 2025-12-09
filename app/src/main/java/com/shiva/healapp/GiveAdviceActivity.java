package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GiveAdviceActivity extends AppCompatActivity {

    EditText etAdvice;
    Button btnSubmitAdvice;
    DBHelper db;
    int userId, doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_advice);

        db = new DBHelper(this);

        userId = getIntent().getIntExtra("user_id", -1);

        SharedPreferences sp = getSharedPreferences("doctor_session", MODE_PRIVATE);
        doctorId = sp.getInt("doctor_id", -1);

        etAdvice = findViewById(R.id.etAdvice);
        btnSubmitAdvice = findViewById(R.id.btnSubmitAdvice);

        btnSubmitAdvice.setOnClickListener(v -> {
            String adviceText = etAdvice.getText().toString().trim();

            if (adviceText.isEmpty()) {
                Toast.makeText(this, "Please enter advice", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = db.insertAdvice(userId, doctorId, adviceText);

            if (result > 0) {
                Toast.makeText(this, "Advice submitted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to submit advice", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

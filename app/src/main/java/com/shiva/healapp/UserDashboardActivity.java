package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserDashboardActivity extends AppCompatActivity {

    Button btnTrackHealth, btnViewHistory, btnInsights, btnProfile, btnExportReport, btnViewAdvice, btnLogout;
    TextView txtWelcome;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        txtWelcome = findViewById(R.id.txtWelcomeUser);
        btnTrackHealth = findViewById(R.id.btnTrackHealth);
        btnViewHistory = findViewById(R.id.btnViewHistory);
        btnInsights = findViewById(R.id.btnInsights);
        btnProfile = findViewById(R.id.btnProfile);
        btnExportReport = findViewById(R.id.btnExportReport);
        btnViewAdvice = findViewById(R.id.btnViewAdvice);
        btnLogout = findViewById(R.id.btnUserLogout);

        DBHelper db = new DBHelper(this);
        String userName = db.getUserNameById(userId);

        if (userName == null || userName.trim().isEmpty()) {
            txtWelcome.setText("Welcome User");
        } else {
            txtWelcome.setText("Welcome " + userName);
        }


        btnTrackHealth.setOnClickListener(v -> openActivity(TrackHealthActivity.class));
        btnViewHistory.setOnClickListener(v -> openActivity(UserHistoryActivity.class));
        btnInsights.setOnClickListener(v -> openActivity(InsightsActivity.class));
        btnProfile.setOnClickListener(v -> openActivity(UserProfileActivity.class));
        btnExportReport.setOnClickListener(v -> openActivity(ExportReportActivity.class));
        btnViewAdvice.setOnClickListener(v -> openActivity(UserViewAdviceActivity.class));

        btnLogout.setOnClickListener(v -> {
            sp.edit().clear().apply();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    private void openActivity(Class<?> cls) {
        if (userId == -1) {
            startActivity(new Intent(this, UserLoginActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this, cls));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        if (sp.getInt("user_id", -1) == -1) {
            startActivity(new Intent(this, UserLoginActivity.class));
            finish();
        }
    }
}

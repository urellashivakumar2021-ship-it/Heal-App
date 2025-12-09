package com.shiva.healapp;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrackHealthActivity extends AppCompatActivity {

    EditText etSleep, etWater, etStress, etDiet, etActivity, etMood, etHeart;
    Button btnSave, btnAdvice;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_health);

        db = new DBHelper(this);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        if (userId == -1) {
            finish();
            return;
        }

        etSleep = findViewById(R.id.etSleep);
        etWater = findViewById(R.id.etWater);
        etStress = findViewById(R.id.etStress);
        etDiet = findViewById(R.id.etDiet);
        etActivity = findViewById(R.id.etActivity);
        etMood = findViewById(R.id.etMood);
        etHeart = findViewById(R.id.etHeart);

        btnSave = findViewById(R.id.btnSaveHealth);
        btnAdvice = findViewById(R.id.btnAdvice);

        btnSave.setOnClickListener(v -> saveLog());
        btnAdvice.setOnClickListener(v -> generateAdvice());
    }

    private void saveLog() {
        try {
            int sleep = Integer.parseInt(etSleep.getText().toString().trim());
            double water = Double.parseDouble(etWater.getText().toString().trim());
            int stress = Integer.parseInt(etStress.getText().toString().trim());
            String diet = etDiet.getText().toString().trim();
            String activity = etActivity.getText().toString().trim();
            String mood = etMood.getText().toString().trim();
            int heart = Integer.parseInt(etHeart.getText().toString().trim());

            long id = db.insertHealthLog(userId, sleep, water, stress, diet, activity, mood, heart);

            Toast.makeText(this, id > 0 ? "Saved successfully" : "Save failed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateAdvice() {
        StringBuilder advice = new StringBuilder();

        // Sleep
        try {
            int sleep = Integer.parseInt(etSleep.getText().toString().trim());
            if (sleep < 6) advice.append("• Try to get at least 7 hours of sleep.\n");
            else advice.append("• Your sleep duration looks good.\n");
        } catch (Exception ignored) {}

        // Water
        try {
            double water = Double.parseDouble(etWater.getText().toString().trim());
            if (water < 2) advice.append("• Drink at least 2 liters of water daily.\n");
            else advice.append("• Good hydration level.\n");
        } catch (Exception ignored) {}

        // Stress
        try {
            int stress = Integer.parseInt(etStress.getText().toString().trim());
            if (stress > 6) advice.append("• High stress detected—try relaxation exercises.\n");
            else advice.append("• Stress level looks manageable.\n");
        } catch (Exception ignored) {}

        // Diet
        String diet = etDiet.getText().toString().trim();
        if (!diet.isEmpty() && diet.length() < 5)
            advice.append("• Add more healthy foods to your diet.\n");

        // Activity
        String activity = etActivity.getText().toString().trim();
        if (!activity.isEmpty() && activity.length() < 4)
            advice.append("• Try doing at least 30 minutes of physical activity.\n");

        // Mood
        String mood = etMood.getText().toString().trim();
        if (mood.equalsIgnoreCase("sad") || mood.equalsIgnoreCase("low"))
            advice.append("• Take a break and do something relaxing.\n");

        // Heart Rate
        try {
            int hr = Integer.parseInt(etHeart.getText().toString().trim());
            if (hr > 100) advice.append("• Your heart rate is high. Monitor it regularly.\n");
            else advice.append("• Heart rate looks normal.\n");
        } catch (Exception ignored) {}

        if (advice.length() == 0) {
            advice.append("• Enter more details to get personalized advice.");
        }

        // Show advice in a dialog
        new AlertDialog.Builder(this)
                .setTitle("Your Health Advice")
                .setMessage(advice.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        if (sp.getInt("user_id", -1) == -1) {
            finish();
        }
    }
}

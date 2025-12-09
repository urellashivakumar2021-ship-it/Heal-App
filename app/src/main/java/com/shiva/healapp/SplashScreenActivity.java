package com.shiva.healapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for 1.5 seconds, then open MainActivity
        new android.os.Handler().postDelayed(
                () -> {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                },
                1500 // 1.5 seconds
        );
    }
}

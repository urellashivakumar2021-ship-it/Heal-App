package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnGoUser, btnGoDoctor, btnGoAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoUser = findViewById(R.id.btnGoUser);
        btnGoDoctor = findViewById(R.id.btnGoDoctor);
        btnGoAdmin = findViewById(R.id.btnGoAdmin);

        btnGoUser.setOnClickListener(v ->
                startActivity(new Intent(this, UserLoginActivity.class)));

        btnGoDoctor.setOnClickListener(v ->
                startActivity(new Intent(this, DoctorLoginActivity.class)));

        btnGoAdmin.setOnClickListener(v ->
                startActivity(new Intent(this, AdminLoginActivity.class)));
    }
}

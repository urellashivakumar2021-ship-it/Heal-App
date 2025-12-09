package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegName, etRegEmail, etRegPassword;
    Button btnRegisterUser, btnGotoLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);

        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnGotoLogin = findViewById(R.id.btnGotoLogin);

        btnRegisterUser.setOnClickListener(v -> {
            String name = etRegName.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String pwd = etRegPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }

            long res = db.registerUser(name, email, pwd);
            if (res > 0) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration failed (Email Exists)", Toast.LENGTH_SHORT).show();
            }
        });

        btnGotoLogin.setOnClickListener(v ->
                startActivity(new Intent(this, UserLoginActivity.class))
        );
    }
}

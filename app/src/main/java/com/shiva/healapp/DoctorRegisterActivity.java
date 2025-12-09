package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorRegisterActivity extends AppCompatActivity {

    EditText etDocName, etDocEmail, etDocPassword, etDocSpecialization, etDocPhone;
    Button btnDoctorRegister;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        db = new DBHelper(this);

        etDocName = findViewById(R.id.etDocName);
        etDocEmail = findViewById(R.id.etDocEmail);
        etDocPassword = findViewById(R.id.etDocPassword);
        etDocSpecialization = findViewById(R.id.etDocSpecialization);
        etDocPhone = findViewById(R.id.etDocPhone);
        btnDoctorRegister = findViewById(R.id.btnDoctorRegister);

        btnDoctorRegister.setOnClickListener(v -> {
            String name = etDocName.getText().toString().trim();
            String email = etDocEmail.getText().toString().trim();
            String pass = etDocPassword.getText().toString().trim();
            String spec = etDocSpecialization.getText().toString().trim();
            String phone = etDocPhone.getText().toString().trim();

            long result = db.registerDoctor(name, email, pass, spec, phone);

            if (result > 0) {
                Toast.makeText(this, "Doctor Registered", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

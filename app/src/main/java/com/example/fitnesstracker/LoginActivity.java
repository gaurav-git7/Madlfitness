package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username from the EditText
                android.widget.EditText etEmail = findViewById(R.id.etEmail);
                String username = etEmail.getText().toString();

                // Navigate to MainActivity (Dashboard)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish(); // Close LoginActivity
            }
        });
    }
}

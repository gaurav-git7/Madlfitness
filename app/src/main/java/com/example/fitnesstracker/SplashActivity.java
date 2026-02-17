package com.example.fitnesstracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 2-second delay before checking login state
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Check SharedPreferences for login state
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false);

            Intent intent;
            if (isLoggedIn) {
                // User is already logged in — skip to Dashboard
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                // Not logged in — show Login screen
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, 2000);
    }
}

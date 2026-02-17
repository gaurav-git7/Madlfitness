package com.example.fitnesstracker;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPreferences";
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Read user info from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userEmail = prefs.getString("USER_EMAIL", "");
        int timerMinutes = prefs.getInt("DEFAULT_TIMER_MINUTES", 0);
        boolean remindersActive = prefs.getBoolean("REMINDERS_ACTIVE", false);
        boolean widgetEnabled = prefs.getBoolean("WIDGET_NOTIFICATIONS_ENABLED", false);

        if (!userEmail.isEmpty()) {
            android.widget.Toast.makeText(this, "Welcome " + userEmail, android.widget.Toast.LENGTH_SHORT).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        viewPager = findViewById(R.id.viewPagerMain);
        bottomNav = findViewById(R.id.bottom_navigation);

        // Set up ViewPager2 with adapter
        viewPager.setAdapter(new ScreenPagerAdapter(this));
        viewPager.setOffscreenPageLimit(4); // Keep all fragments alive

        // Sync ViewPager2 swipe → BottomNav highlight
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNav.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.nav_workout);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.nav_history);
                        break;
                    case 3:
                        bottomNav.setSelectedItemId(R.id.nav_profile);
                        break;
                }
            }
        });

        // Sync BottomNav tap → ViewPager2 page
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                viewPager.setCurrentItem(0, true);
            } else if (itemId == R.id.nav_workout) {
                viewPager.setCurrentItem(1, true);
            } else if (itemId == R.id.nav_history) {
                viewPager.setCurrentItem(2, true);
            } else if (itemId == R.id.nav_profile) {
                viewPager.setCurrentItem(3, true);
            }
            return true;
        });
    }

    /**
     * FragmentStateAdapter that provides all 4 main screens.
     */
    private static class ScreenPagerAdapter extends FragmentStateAdapter {

        public ScreenPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new WorkoutFragment();
                case 2:
                    return new HistoryFragment();
                case 3:
                    return new ProfileFragment();
                default:
                    return new DashboardFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
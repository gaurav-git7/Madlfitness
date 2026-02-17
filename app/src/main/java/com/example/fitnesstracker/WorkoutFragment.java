package com.example.fitnesstracker;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkoutFragment extends Fragment {

    // Timer state variables
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    // View references
    private TextView tvTimer;
    private TextView tvCalories;
    private TextView tvDistance;
    private TextView tvPace;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        tvTimer = view.findViewById(R.id.tvTimer);
        tvCalories = view.findViewById(R.id.tvCalories);
        tvDistance = view.findViewById(R.id.tvDistance);
        tvPace = view.findViewById(R.id.tvPace);

        Button btnStart = view.findViewById(R.id.btnStart);
        Button btnPause = view.findViewById(R.id.btnPause);
        Button btnStop = view.findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> running = true);

        btnPause.setOnClickListener(v -> running = false);

        btnStop.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hold button to stop workout", Toast.LENGTH_SHORT).show();
        });

        btnStop.setOnLongClickListener(v -> {
            boolean wasActive = running || seconds > 0;
            running = false;

            if (wasActive) {
                showSaveDialog();
            }
            return true;
        });

        // Restore state if available
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

        // Laps Logic
        final android.widget.TextView tvLaps = view.findViewById(R.id.tvLaps);
        Button btnLap = view.findViewById(R.id.btnLap);

        btnLap.setOnClickListener(v -> {
            if (running) {
                String currentLaps = tvLaps.getText().toString();
                if (currentLaps.contains("Press Lap"))
                    currentLaps = "";

                String timestamp = tvTimer.getText().toString();
                int lapCount = currentLaps.split("\n").length + 1;

                String newLap = String.format(Locale.getDefault(), "Lap %d: %s\n", lapCount, timestamp);
                tvLaps.setText(currentLaps + newLap);

                Toast.makeText(getContext(), "Lap Recorded!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Start timer to record laps", Toast.LENGTH_SHORT).show();
            }
        });

        // Removed GestureDetector execution

        return view;
    }

    private void showSaveDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Workout Finished")
                .setMessage("Do you want to save this workout?")
                .setPositiveButton("Save", (dialog, which) -> saveWorkout())
                .setNegativeButton("Discard", (dialog, which) -> resetWorkout())
                .setCancelable(false)
                .show();
    }

    private void saveWorkout() {
        // Prepare data
        String date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        String duration = tvTimer.getText().toString();
        double calories = seconds * 0.15;
        double distance = seconds * 0.003; // Mock: 3 meters per second

        // Save to Manager
        WorkoutSession session = new WorkoutSession(date, duration, calories, distance);
        WorkoutManager.getInstance().addWorkout(session);

        Toast.makeText(getContext(), "Workout Saved to History!", Toast.LENGTH_SHORT).show();
        resetWorkout();
    }

    private void resetWorkout() {
        seconds = 0;
        updateView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    private void runTimer() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateView();

                if (running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateView() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        tvTimer.setText(time);

        double calories = seconds * 0.15;
        tvCalories.setText(String.format(Locale.getDefault(), "%.1f Kcal", calories));

        // Mock Distance update
        double distanceKm = seconds * 0.003;
        tvDistance.setText(String.format(Locale.getDefault(), "%.2f km", distanceKm));

        // Mock Pace (just a placeholder)
        tvPace.setText("05:30 /km");
    }
}

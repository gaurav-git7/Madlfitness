package com.example.fitnesstracker;

public class WorkoutSession {
    private String date;
    private String duration; // e.g., "00:32:15"
    private double calories;
    private double distance; // Placeholder for now

    public WorkoutSession(String date, String duration, double calories, double distance) {
        this.date = date;
        this.duration = duration;
        this.calories = calories;
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public double getCalories() {
        return calories;
    }

    public double getDistance() {
        return distance;
    }
}

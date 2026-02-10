package com.example.fitnesstracker;

import java.util.ArrayList;
import java.util.List;

public class WorkoutManager {
    private static WorkoutManager instance;
    private List<WorkoutSession> workoutSessions;

    private WorkoutManager() {
        workoutSessions = new ArrayList<>();
    }

    public static synchronized WorkoutManager getInstance() {
        if (instance == null) {
            instance = new WorkoutManager();
        }
        return instance;
    }

    public void addWorkout(WorkoutSession session) {
        // Add to the beginning of the list so newest is first
        workoutSessions.add(0, session);
    }

    public List<WorkoutSession> getWorkouts() {
        return workoutSessions;
    }

    public void removeWorkout(int index) {
        if (index >= 0 && index < workoutSessions.size()) {
            workoutSessions.remove(index);
        }
    }

    public void addWorkout(int index, WorkoutSession session) {
        if (index >= 0 && index <= workoutSessions.size()) {
            workoutSessions.add(index, session);
        }
    }
}

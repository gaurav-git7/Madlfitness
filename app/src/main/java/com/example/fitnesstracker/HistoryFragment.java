package com.example.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView rvHistory;
    private TextView tvEmptyState;
    private HistoryAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        rvHistory = view.findViewById(R.id.rvHistory);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        loadWorkoutHistory();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when returning to this fragment
        loadWorkoutHistory();
    }

    private void loadWorkoutHistory() {
        List<WorkoutSession> sessions = WorkoutManager.getInstance().getWorkouts();

        if (sessions.isEmpty()) {
            rvHistory.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);

            adapter = new HistoryAdapter(sessions);
            rvHistory.setAdapter(adapter);

            // Add Swipe to Delete
            new androidx.recyclerview.widget.ItemTouchHelper(
                    new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(0,
                            androidx.recyclerview.widget.ItemTouchHelper.LEFT
                                    | androidx.recyclerview.widget.ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@androidx.annotation.NonNull RecyclerView recyclerView,
                                @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder,
                                @androidx.annotation.NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder,
                                int direction) {
                            int position = viewHolder.getAdapterPosition();
                            WorkoutSession deletedSession = sessions.get(position);

                            // Remove from Adapter and Manager
                            adapter.removeItem(position);
                            WorkoutManager.getInstance().removeWorkout(position);

                            // Show Snackbar with Undo
                            com.google.android.material.snackbar.Snackbar
                                    .make(rvHistory, "Workout deleted",
                                            com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        // Restore to Adapter and Manager
                                        adapter.restoreItem(deletedSession, position);
                                        WorkoutManager.getInstance().addWorkout(position, deletedSession);
                                    }).show();
                        }
                    }).attachToRecyclerView(rvHistory);
        }
    }
}

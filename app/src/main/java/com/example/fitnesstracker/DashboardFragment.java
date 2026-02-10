package com.example.fitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * DashboardFragment: Handles the UI and logic for the Fitness Tracker
 * Dashboard.
 * Created for Experiment 2 to demonstrate ConstraintLayout and Responsive
 * LinearLayout Weighting.
 */
public class DashboardFragment extends Fragment {

    // View references for UI elements
    private ProgressBar progressBarSteps;
    private TextView tvStepCount;
    private TextView tvCalories;
    private TextView tvDistance;
    private TextView tvActiveTime;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize view references using findViewById
        initializeViews(view);

        // Placeholder for future logic (e.g., Sensor listeners, Database updates)
        updateDashboardData();

        return view;
    }

    /**
     * Finds and links XML views to Java objects.
     * 
     * @param root The root view of the fragment layout.
     */
    private void initializeViews(View root) {
        progressBarSteps = root.findViewById(R.id.progressBarSteps);
        tvStepCount = root.findViewById(R.id.tvStepCount);

        // Setup ViewPager2
        androidx.viewpager2.widget.ViewPager2 viewPager = root.findViewById(R.id.viewPagerStats);
        DashboardPagerAdapter adapter = new DashboardPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    private void updateDashboardData() {
        int currentSteps = 4500;
        int stepGoal = 10000;

        progressBarSteps.setMax(stepGoal);
        progressBarSteps.setProgress(currentSteps);

        tvStepCount.setText(String.format("%,d", currentSteps));
    }

    // Inner Adapter Class for ViewPager2
    private static class DashboardPagerAdapter
            extends androidx.recyclerview.widget.RecyclerView.Adapter<DashboardPagerAdapter.StatViewHolder> {

        private final String[] icons = { "🔥", "📍", "⏱️" };
        private final String[] values = { "320 Kcal", "3.5 km", "45 mins" };
        private final String[] labels = { "Calories Burned", "Distance Covered", "Active Time" };

        @NonNull
        @Override
        public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_stat, parent, false);
            return new StatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {
            holder.tvIcon.setText(icons[position]);
            holder.tvValue.setText(values[position]);
            holder.tvLabel.setText(labels[position]);
        }

        @Override
        public int getItemCount() {
            return icons.length;
        }

        static class StatViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            TextView tvIcon, tvValue, tvLabel;

            public StatViewHolder(@NonNull View itemView) {
                super(itemView);
                tvIcon = itemView.findViewById(R.id.tvStatIcon);
                tvValue = itemView.findViewById(R.id.tvStatValue);
                tvLabel = itemView.findViewById(R.id.tvStatLabel);
            }
        }
    }
}
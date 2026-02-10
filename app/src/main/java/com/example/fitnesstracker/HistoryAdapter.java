package com.example.fitnesstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<WorkoutSession> sessions;

    public HistoryAdapter(List<WorkoutSession> sessions) {
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutSession session = sessions.get(position);

        holder.tvDate.setText(session.getDate());
        holder.tvDuration.setText(session.getDuration());
        holder.tvDistance.setText(String.format(Locale.getDefault(), "%.2f km", session.getDistance()));
        holder.tvCalories.setText(String.format(Locale.getDefault(), "%.1f Kcal", session.getCalories()));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void removeItem(int position) {
        sessions.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(WorkoutSession session, int position) {
        sessions.add(position, session);
        notifyItemInserted(position);
    }

    public List<WorkoutSession> getData() {
        return sessions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDuration, tvDistance, tvCalories;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvDistance = itemView.findViewById(R.id.tvDistanceVal);
            tvCalories = itemView.findViewById(R.id.tvCaloriesVal);
        }
    }
}

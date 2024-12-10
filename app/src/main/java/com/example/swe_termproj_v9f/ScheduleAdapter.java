package com.example.swe_termproj_v9f;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<TimetableEntry> timetableEntries;

    public ScheduleAdapter(List<TimetableEntry> timetableEntries) {
        this.timetableEntries = timetableEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimetableEntry entry = timetableEntries.get(position);
        holder.className.setText(entry.getClassName());
        holder.classTime.setText(entry.getClassTime());
        holder.classLocation.setText(entry.getClassLocation());
    }

    @Override
    public int getItemCount() {
        return timetableEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView className, classTime, classLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            classTime = itemView.findViewById(R.id.classTime);
            classLocation = itemView.findViewById(R.id.classLocation);
        }
    }
}

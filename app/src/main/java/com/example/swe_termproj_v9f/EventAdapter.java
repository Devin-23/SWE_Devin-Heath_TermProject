package com.example.swe_termproj_v9f;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<String> events;
    private final OnEventActionListener listener;

    // Constructor accepting the events list and listener
    public EventAdapter(List<String> events, OnEventActionListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String event = events.get(position);
        holder.eventTextView.setText(event);

        // Handle Edit button click
        holder.editButton.setOnClickListener(v -> listener.onEdit(event));

        // Handle Delete button click
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(event));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface OnEventActionListener {
        void onEdit(String event);
        void onDelete(String event);
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTextView;
        Button editButton, deleteButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTextView = itemView.findViewById(R.id.eventTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

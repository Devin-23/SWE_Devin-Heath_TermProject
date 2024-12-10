package com.example.swe_termproj_v9f;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {

    private List<ProgramItem> programList;
    private Context context;
    private OnProgramClickListener onProgramClickListener;

    public interface OnProgramClickListener {
        void onProgramClick(String url);
    }

    public ProgramAdapter(Context context, List<ProgramItem> programList, OnProgramClickListener listener) {
        this.context = context;
        this.programList = programList;
        this.onProgramClickListener = listener;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.program_item, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        ProgramItem program = programList.get(position);
        holder.programName.setText(program.getProgramName());
        holder.programDescription.setText(program.getProgramDescription());
        holder.itemView.setOnClickListener(v -> onProgramClickListener.onProgramClick(program.getUrl()));
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView programName, programDescription;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.programName);
            programDescription = itemView.findViewById(R.id.programDescription);
        }
    }
}


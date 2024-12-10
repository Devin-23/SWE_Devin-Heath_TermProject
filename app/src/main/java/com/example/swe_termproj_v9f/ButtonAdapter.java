package com.example.swe_termproj_v9f;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private List<ButtonItem> buttonList;
    private Context context;

    public ButtonAdapter(Context context, List<ButtonItem> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        ButtonItem buttonItem = buttonList.get(position);
        holder.textView.setText(buttonItem.getButtonName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, buttonItem.getDestinationActivity());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mainOptions);
        }
    }
}

// ProgramAdapter.java
package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {
    private ArrayList<Program> programList;
    private final OnProgramClickListener clickListener;
    private final OnDeleteClickListener deleteListener;

    public void updatePrograms(ArrayList<Program> newPrograms) {
        this.programList = newPrograms;
        notifyDataSetChanged();
    }

    public interface OnProgramClickListener {
        void onProgramClick(Program program);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ProgramAdapter(ArrayList<Program> programList, OnProgramClickListener clickListener, OnDeleteClickListener deleteListener) {
        this.programList = programList;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        Program program = programList.get(position);
        holder.bind(program, clickListener, deleteListener);
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView programNameTextView;
        ImageView deleteProgramButton;
        ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            programNameTextView = itemView.findViewById(R.id.programNameTextView);
            deleteProgramButton = itemView.findViewById(R.id.deleteProgramButton);
        }
        void bind(final Program program, final OnProgramClickListener clickListener, final OnDeleteClickListener deleteListener) {
            programNameTextView.setText(program.getName());
            itemView.setOnClickListener(v -> clickListener.onProgramClick(program));
            deleteProgramButton.setOnClickListener(v -> deleteListener.onDeleteClick(getAdapterPosition()));
        }
    }
}

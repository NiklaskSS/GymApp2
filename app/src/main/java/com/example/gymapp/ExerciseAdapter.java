// ExerciseAdapter.java
package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<Exercise> exerciseList;
    private final OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ExerciseAdapter(ArrayList<Exercise> exerciseList, OnDeleteClickListener deleteListener) {
        this.exerciseList = exerciseList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.bind(exercise, deleteListener);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameTextView, repsTextView;
        ImageView deleteExerciseButton;
        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            repsTextView = itemView.findViewById(R.id.repsTextView);
            deleteExerciseButton = itemView.findViewById(R.id.deleteExerciseButton);
        }
        void bind(final Exercise exercise, final OnDeleteClickListener listener) {
            exerciseNameTextView.setText(exercise.getName());
            repsTextView.setText("Reps: " + exercise.getReps());
            deleteExerciseButton.setOnClickListener(v -> listener.onDeleteClick(getAdapterPosition()));
        }
    }
}
